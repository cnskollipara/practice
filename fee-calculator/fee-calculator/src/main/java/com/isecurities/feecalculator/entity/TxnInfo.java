package com.isecurities.feecalculator.entity;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.isecurities.feecalculator.Utils;
import com.isecurities.feecalculator.bean.Priority;
import com.isecurities.feecalculator.bean.TxnType;

@Entity
public class TxnInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String extTxnId;
    private String clientId;
    private String securityId;
    private TxnType txnType;
    private Date txnDate;
    private String marketVal;
    private Priority priority;
    private Long processingFee;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getExtTxnId() {
		return extTxnId;
	}
	public void setExtTxnId(String extTxnId) {
		this.extTxnId = extTxnId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSecurityId() {
		return securityId;
	}
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
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
	public String getMarketVal() {
		return marketVal;
	}
	public void setMarketVal(String marketVal) {
		this.marketVal = marketVal;
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
		return "TxnInfo [id=" + id + ", extTxnId=" + extTxnId + ", clientId=" + clientId + ", securityId=" + securityId
				+ ", txnType=" + txnType + ", txnDate=" + txnDate + ", marketVal=" + marketVal + ", priority="
				+ priority + ", processingFee=" + processingFee + "]";
	}
}
