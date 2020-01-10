package com.abc.util;

import static java.lang.Math.abs;

public class StringFormatter {
    public static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
