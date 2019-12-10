package com.abc.shared;

public class Methods {
    public static String toDollars(double d){
        return String.format("$%,.2f", d);
    }
}
