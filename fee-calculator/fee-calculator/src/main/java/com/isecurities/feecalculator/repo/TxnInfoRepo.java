package com.isecurities.feecalculator.repo;

import com.isecurities.feecalculator.bean.TxnInfoStats;
import com.isecurities.feecalculator.entity.TxnInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TxnInfoRepo extends CrudRepository<TxnInfo, Long> {

    /*
    * SELECT * FROM TXN_INFO;
    * 
    * SELECT CLIENT_ID, TXN_TYPE, TXN_DATE, PRIORITY, SUM(PROCESSING_FEE) PREOCESSING_FEE, GROUP_CONCAT(CONCAT(EXT_TXN_ID, '-', PROCESSING_FEE) SEPARATOR CHAR(10)) as DETAILS
    * FROM TXN_INFO
    * GROUP BY CLIENT_ID, TXN_TYPE, TXN_DATE, PRIORITY;
    * */
    @Query(//nativeQuery = true,
    value = "SELECT new com.isecurities.feecalculator.bean.TxnInfoStats(clientId, txnType, txnDate, priority, SUM(processingFee )) " +
            " FROM TxnInfo" +
            " GROUP BY clientId, txnType, txnDate, priority")
    public List<TxnInfoStats> getTxnInfoSummary();

    @Query("SELECT t FROM TxnInfo t WHERE t.priority = 1 AND t.processingFee != :intradayFee AND (t.txnType = 0 OR t.txnType = 1)")
    public List<TxnInfo> getTxnsForIntradayProcessing(@Param("intradayFee") Long intradayFee);
}