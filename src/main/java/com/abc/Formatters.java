package com.abc;

import static java.lang.Math.abs;

/**
 * Used for formatting stuff for printing
 * Basically just a class for toDollars (which I use in two separate classes) and format (originally in Bank)
 */
public class Formatters {

    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public static String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

}
