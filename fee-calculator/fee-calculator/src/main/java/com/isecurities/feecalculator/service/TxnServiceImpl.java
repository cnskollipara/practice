package com.isecurities.feecalculator.service;

import static com.isecurities.feecalculator.Utils.CSV;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.isecurities.feecalculator.Utils;
import com.isecurities.feecalculator.bean.Priority;
import com.isecurities.feecalculator.bean.TxnInfoStats;
import com.isecurities.feecalculator.bean.TxnType;
import com.isecurities.feecalculator.entity.TxnInfo;
import com.isecurities.feecalculator.mapper.CSVMapper;
import com.isecurities.feecalculator.mapper.TxnInfoMapper;
import com.isecurities.feecalculator.repo.TxnInfoRepo;

@Service
public class TxnServiceImpl implements TxnService {

    @Autowired
    private TxnInfoRepo txnInfoRepo;

    @Value("${output.dir}")
    private String OUTPUT_FOLDER;

    @Value("${output.summary-header}")
    private String SUMMARY_HEADER;

    @Value("${processing-fee.priority}")
    private Long PRIORITY_FEE;

    @Value("${processing-fee.intraday}")
    private Long INTRADAY_FEE;

    @Value("${processing-fee.normal.sell}")
    private Long NORMAL_SELL_FEE;

    @Value("${processing-fee.normal.buy}")
    private Long NORMAL_BUY_FEE;

    @Value("${processing-fee.normal.withdraw}")
    private Long NORMAL_WITHDRAW_FEE;

    @Value("${processing-fee.normal.deposit}")
    private Long NORMAL_DEPOSIT_FEE;

    @Override
    public List<TxnInfo> parseFile(Path path, String fileType) {
        try {
            TxnInfoMapper txnInfoMapper = null;
            // skip the header of the csv
            switch (fileType) {
                case CSV:
                    txnInfoMapper = new CSVMapper();
            }
            if (txnInfoMapper == null) {
                return null;
            }
            TxnInfoMapper mapper = txnInfoMapper;
            List<TxnInfo> txnInfos = Files.lines(path).skip(1).map(l -> mapper.map(l)).collect(Collectors.toList());
            return txnInfos;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void parseAndSave(Path path, String fileType) {
        List<TxnInfo> txnInfos = parseFile(path, fileType);
        //Set processingFee as per TxnType as default
        txnInfos.stream().forEach(txn -> {

            if (txn.getPriority().equals(Priority.Y)) {
                txn.setProcessingFee(PRIORITY_FEE);
            }else if (txn.getTxnType().equals(TxnType.DEPOSIT)) {
                txn.setProcessingFee(NORMAL_DEPOSIT_FEE);
            }else if (txn.getTxnType().equals(TxnType.WITHDRAW)) {
                txn.setProcessingFee(NORMAL_WITHDRAW_FEE);
            }else if (txn.getTxnType().equals(TxnType.BUY)) {
                txn.setProcessingFee(NORMAL_BUY_FEE);
            }else if (txn.getTxnType().equals(TxnType.SELL)) {
                txn.setProcessingFee(NORMAL_SELL_FEE);
            }
        });
        txnInfoRepo.saveAll(txnInfos);
        //Process Buy & Sell txn's subjected to Intraday
        processIntradayTxns();
    }

    private void processIntradayTxns() {
        List<TxnInfo> txns = txnInfoRepo.getTxnsForIntradayProcessing(INTRADAY_FEE);
        Map<String, List<TxnInfo>> buyMap = new HashMap<>();
        Map<String, List<TxnInfo>> sellMap = new HashMap<>();
        txns.stream()
                .filter(txn -> (txn.getPriority().equals(Priority.N)
                        && (txn.getTxnType().equals(TxnType.BUY) || txn.getTxnType().equals(TxnType.SELL))))
                .forEach(txn -> {
                    String intradayKey = getIntradayKey(txn);
                    List<TxnInfo> compensatoryTxns = null;
                    Map<String, List<TxnInfo>> preserveMap = null;
                    if (txn.getTxnType().equals(TxnType.BUY)) {
                        preserveMap = buyMap;
                        compensatoryTxns = sellMap.get(intradayKey);
                    } else if (txn.getTxnType().equals(TxnType.SELL)) {
                        preserveMap = sellMap;
                        compensatoryTxns = buyMap.get(intradayKey);
                    }
                    if (!CollectionUtils.isEmpty(compensatoryTxns)) {
                        //Get the compensatory Txn form the list
                        TxnInfo compensatoryTxn = compensatoryTxns.get(0);
                        //Update Txn and Compensatory with Intraday fee
                        compensatoryTxn.setProcessingFee(INTRADAY_FEE);
                        txn.setProcessingFee(INTRADAY_FEE);
                        //remove the compensatory from the list
                        compensatoryTxns.remove(0);
                    } else {
                        List<TxnInfo> actualTxns = preserveMap.getOrDefault(intradayKey, new ArrayList<>());
                        actualTxns.add(txn);
                        preserveMap.put(intradayKey, actualTxns);
                    }
                });
        txnInfoRepo.saveAll(txns);
    }

    private String getIntradayKey(TxnInfo txn) {
        return txn.getClientId() + "-" + txn.getSecurityId() + "-" + txn.getTxnDate();
    }

    @Override
    public List<TxnInfoStats> getTxnSummary() {
    	List<TxnInfoStats> infoStats = txnInfoRepo.getTxnInfoSummary();
    	writeToFile(infoStats);
    	return infoStats;
    }

	private void writeToFile(List<TxnInfoStats> infoStats) {
		File file = new File(OUTPUT_FOLDER);
		boolean isCreated = file.mkdirs();
		try(FileWriter writer = new FileWriter(OUTPUT_FOLDER + System.currentTimeMillis() + ".csv");) {
			writer.append(SUMMARY_HEADER + Utils.LINE_SEPERATOR);
			String data = infoStats.stream().map(TxnInfoStats::toCsvRow).collect(Collectors.joining(Utils.LINE_SEPERATOR));
			writer.append(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
