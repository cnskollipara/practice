package com.isecurities.feecalculator.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
public class TxnInfoStats {
    private String clientId;
    private TxnType txnType;
    private Date txnDate;
    private Priority priority;
    private Long processingFee;

    public TxnInfoStats(String clientId, TxnType txnType, Date txnDate, Priority priority, Long processingFee) {
        this.clientId = clientId;
        this.txnType = txnType;
        this.txnDate = txnDate;
        this.priority = priority;
        this.processingFee = processingFee;
    }
}
