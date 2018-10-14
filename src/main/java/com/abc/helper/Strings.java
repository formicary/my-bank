package com.abc.helper;

import static java.lang.Math.abs;

/**
 * Helper class for String manipulation
 */
public class Strings {

    /**
     * Pluralises a noun String conditionally based on a count parameter
     * @param count the integer number of instances of that noun
     * @param noun the noun String
     * @return the noun in plural or singular form based on the count
     */
    public static String pluralize(int count, String noun) {
        return count == 1 ? noun : noun + "s";
    }

    /**
     * Returns a double, formatted as dollar currency
     * @param d the double value to convert into a dollar string
     * @return a String representing the double parameter in dollars
     */
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
