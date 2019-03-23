package com.isecurities.feecalculator.mapper;

import com.isecurities.feecalculator.entity.TxnInfo;

public interface TxnInfoMapper {
    public TxnInfo map(String line);
}
