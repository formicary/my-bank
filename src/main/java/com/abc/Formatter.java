package com.abc;

import static java.lang.Math.abs;

/**
 * Sole purpose of this class is to display number in dollar currency format.
 */

public class Formatter {

    /**
     * Convert pounds to dollars.
     * @param d The number to be formatted.
     * @return The formatted string.
     */
    public static String toDollars(double d){

        return String.format("$%,.2f", abs(d));
    }
}
