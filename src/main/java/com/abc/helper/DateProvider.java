package com.abc.helper;

import java.util.Calendar;
import java.util.Date;

/**
 * Static class that returns the current Date
 */
public class DateProvider {

    private static final long DAYS = 1000 * 60 * 60 * 24;

    /**
     * Returns the current Date
     * @return the Calendar's Date instance
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Returns the current Date, with an offset in the number of days
     * @param days the amount of days to offset the current Date by
     * @return a Date Object for the current time, offset by days
     */
    public static Date offset(int days) {
        return new Date(now().getTime() + (days * DAYS));
    }

}
