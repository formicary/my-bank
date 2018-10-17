// Manages all currency-related issues, such as rounding, conversion.

package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;


import static java.lang.Math.abs;

public class CurrencyManager {

    public BigDecimal roundBigDecimal(BigDecimal number){
        return number.setScale(2, RoundingMode.HALF_UP);
    }

    public String toDollarsAbs(BigDecimal number){
        String s = "$";
        number = number.setScale(2, RoundingMode.HALF_UP);
        DecimalFormat f = new DecimalFormat("#,##0.00");
        return s + f.format(number.abs());
    }
}
