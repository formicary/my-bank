package com.mybank.util;

public class FormatHelper {

    /**
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is larger than 1 add an 's' at the end otherwise just return the word
     *
     * @param number the number
     * @param word the word
     * @return the formatted string
     */
    public static String format(int number, String word) {
        return number + " " + (number > 1 ? word + "s" : word);
    }

}
