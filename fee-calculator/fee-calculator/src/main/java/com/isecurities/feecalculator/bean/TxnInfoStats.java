package com.isecurities.feecalculator.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.isecurities.feecalculator.Utils;

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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public TxnType getTxnType() {
		return txnType;
	}

	public void setTxnType(TxnType txnType) {
		this.txnType = txnType;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Long getProcessingFee() {
		return processingFee;
	}

	public void setProcessingFee(Long processingFee) {
		this.processingFee = processingFee;
	}

	@Override
	public String toString() {
		return "TxnInfoStats [clientId=" + clientId + ", txnType=" + txnType + ", txnDate=" + txnDate + ", priority="
				+ priority + ", processingFee=" + processingFee + "]";
	}
	
	public String toCsvRow() {
	    return Stream.of(this.clientId, this.txnType.name(), Utils.formatDate(this.txnDate), this.priority.name(), this.processingFee.toString())
	            .collect(Collectors.joining(Utils.COMMA));
	}
}
