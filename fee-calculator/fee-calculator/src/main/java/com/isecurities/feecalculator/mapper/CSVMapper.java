package com.isecurities.feecalculator.mapper;

import com.isecurities.feecalculator.Utils;
import com.isecurities.feecalculator.bean.Priority;
import com.isecurities.feecalculator.bean.TxnType;
import com.isecurities.feecalculator.entity.TxnInfo;

import static com.isecurities.feecalculator.Utils.COMMA;

public class CSVMapper implements TxnInfoMapper {
    @Override
    public TxnInfo map(String line) {
        String[] attrs = line.split(COMMA);
        if (attrs.length < 7) {
            return null;
        }
        TxnInfo item = new TxnInfo();
        item.setExtTxnId(attrs[0].trim());
        item.setClientId(attrs[1].trim());
        item.setSecurityId(attrs[2].trim());
        item.setTxnType(TxnType.valueOf(attrs[3].trim()));
        item.setTxnDate(Utils.toDate(attrs[4].trim()));
        item.setMarketVal(attrs[5].trim());
        item.setPriority(Priority.valueOf(attrs[6].trim()));
        return item;
    }
}
