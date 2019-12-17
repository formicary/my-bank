package com.abc;

import static java.lang.Math.abs;

public class StringUtils {

    public static String toDollars(final double d){
        return String.format("$%,.2f", abs(d));
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    public static String quantityOfNoun(final int number, final String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

}
