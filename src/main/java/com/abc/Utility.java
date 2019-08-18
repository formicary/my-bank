package com.abc;

import static java.lang.Math.abs;

class Utility {
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
