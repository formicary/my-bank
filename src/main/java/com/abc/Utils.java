package com.abc;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public final class Utils {

    // Prevent instantiation of class
    private Utils() {
    }

    public static Date nowDateAndTime() {
        // Return current date and time
        return Calendar.getInstance().getTime();
    }

    public static String toDollars(double d) {
        // Return input value in dollar format with 2dp
        return String.format("$%,.2f", abs(d));
    }

    public static String formatWordPlural(int number, String word) {
        // Make sure correct plural of a word is created based on the number passed in, i.e.
        // if number passed in is 1, just return the word, otherwise add an 's' at the end
        return number + " " + (number == 1 ? word : word + "s");
    }

    public static Date getTomorrowMidnight() {
        // Get an instance of Calendar, set to now
        Calendar tomorrowMidnight = Calendar.getInstance();

        // Reset time to midnight of today
        tomorrowMidnight.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowMidnight.set(Calendar.MINUTE, 0);
        tomorrowMidnight.set(Calendar.SECOND, 0);
        tomorrowMidnight.set(Calendar.MILLISECOND, 0);

        // Add 1 to the days of the month to get midnight of
        // tomorrow and return the Date object
        tomorrowMidnight.add(Calendar.DAY_OF_MONTH, 1);
        return tomorrowMidnight.getTime();
    }
}
