package com.abc.shared;

public class Methods {
    public static String toDollars(double d){
        return String.format("$%,.2f", d);
    }

    public static Double roundTo2dp(double d) {
        double number = Math.round(d * 100.0);
        return number/100.0;
    }
}
