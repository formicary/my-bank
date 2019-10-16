package com.abc;


import java.math.BigDecimal;

public class Conversion {


    static String toDollars(BigDecimal amount) {
        return String.format("$%,.2f", amount);
    }


    public static BigDecimal doubleToBigDecimalConverter(double amount) {
        BigDecimal convertedAmount = new BigDecimal(amount);
        return convertedAmount;
    }
}
