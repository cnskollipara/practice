package com.isecurities.feecalculator;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class Utils {
    public static final String CSV = "csv";
    public static final String COMMA = ",";
    public static final List<String> ALLOWED_FILE_TYPES = Arrays.asList(CSV);
    public static final SimpleDateFormat SDF = new SimpleDateFormat("MM/dd/yyyy");

    public static Date toDate(String s) {
        try {
            return SDF.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
