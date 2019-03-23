package com.isecurities.feecalculator.entity;

import com.isecurities.feecalculator.bean.Priority;
import com.isecurities.feecalculator.bean.TxnType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
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
}
