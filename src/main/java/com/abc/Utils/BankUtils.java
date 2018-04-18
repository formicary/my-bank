package com.abc.Utils;

import java.util.Date;

import static java.lang.Math.abs;

/**
 * Represents a set of utilities.
 */
public class BankUtils {
    /**
     * Returns a string of given value in dollars
     *
     * @param d The value
     *
     * @return The formatted string in dollars.
     */
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }


    /**
     * Formats a sentence given a noun
     *
     * @param frequency The frequency of the noun.
     * @param word The noun.
     *
     * @return The format sentence.
     */
    public static String formatSentence(int frequency, String word) {
        // Make sure correct plural of word is created based on the frequency passed in:
        // If frequency passed in is 1 just return the word otherwise add an 's' at the end
        return frequency + " " + (frequency == 1 ? word : word + "s");
    }

    /**
     * Calculates the number of days between the two given dates.
     *
     * @param firstDate The first date.
     * @param secondDate The second date.
     *
     * @return The number of days between the two given dates.
     */
    public static int calculateDaysBetween(Date firstDate, Date secondDate) {
        return (int)( (secondDate.getTime() - firstDate.getTime()) / (1000 * 60 * 60 * 24));
    }
}
