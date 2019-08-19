package com.abc.utility;

import static java.lang.Math.abs;

public class Utility {
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
