package com.mybank.util;

import static java.lang.Math.abs;

public class CurrencyConverter {

    /**
     *
     * @param d
     * @return
     */
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
