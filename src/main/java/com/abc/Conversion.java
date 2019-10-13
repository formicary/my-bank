package com.abc;


import java.math.BigDecimal;

public class Conversion {


    static String toDollars(BigDecimal amount){
        return String.format("$%,.2f", amount);
    }

    static String format(int number, String word){
        return number + " " + (number == 1 ? word : word + "s");
    }

    public static BigDecimal doubleToBigDecimalConverter(double amount){
        BigDecimal  convertedAmount = new BigDecimal(amount);
        return convertedAmount;
    }
}
