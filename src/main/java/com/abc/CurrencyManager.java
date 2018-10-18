package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CurrencyManager {

    public static BigDecimal roundBigDecimal(BigDecimal number){
        return number.setScale(2, RoundingMode.HALF_UP);
    }

    public String toDollarsAbs(BigDecimal number){
        String s = "$";
        number = number.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat f = new DecimalFormat("#,##0.00");
        return s + f.format(number.abs());
    }
}
