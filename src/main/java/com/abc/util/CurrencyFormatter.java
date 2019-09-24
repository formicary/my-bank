package com.abc.util;

import static java.lang.Math.abs;

/**
 * @project MyBank
 */
public class CurrencyFormatter {

    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
