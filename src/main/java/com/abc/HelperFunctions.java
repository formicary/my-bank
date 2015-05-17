package com.abc;

import static java.lang.Math.abs;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A set of helper functions (mostly formatting).
 * @author Donald Campbell (campbell.donald@gmail.com)
 *
 */
public class HelperFunctions {

	/**
	 * Formats a number and word into human-friendly printable phrase.
	 * @param number Number to translate.
	 * @param word Human-readable word to follow in teh phrase.
	 * @return Phrase complete with number and correctly-formatted word.
	 */
	public static String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    
	/**
	 * Converts a number to a printer-friendly dollar figure.
	 * @param toConvert Amount to convert.
	 * @return A string representing the pretty-printable dollar figure (2.0--$2.00).
	 */
    public static String toDollars(double toConvert){
        return String.format("$%,.2f", abs(toConvert));
    }
    
    /**
     * Calculates the number of julian days between two Date objects.
     * @param date1 First date.
     * @param date2 Second date.
     * @return The number of julian days between date1 and date2.
     */
    public static int daysBetween(Date date1, Date date2) {
    	return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)));
    }
    
    /**
     * Formats a Date object into a pretty-printable String.
     * @param toFormat Date object to convert to pretty-printable.
     * @return A string pretty-printable version of the passed Date object.
     */
    public static String formatDate (Date toFormat) {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	return dateFormat.format(toFormat);
    }
}
