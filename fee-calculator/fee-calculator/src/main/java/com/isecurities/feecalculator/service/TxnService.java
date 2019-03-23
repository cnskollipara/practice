package com.isecurities.feecalculator.service;

import com.isecurities.feecalculator.bean.TxnInfoStats;
import com.isecurities.feecalculator.entity.TxnInfo;

import java.nio.file.Path;
import java.util.List;

public interface TxnService {
    public List<TxnInfo> parseFile(Path path, String fileType);

    public void parseAndSave(Path path, String fileType);

    public List<TxnInfoStats> getTxnSummary();
}
