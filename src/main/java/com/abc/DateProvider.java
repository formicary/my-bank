package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that handles dates
 */
public class DateProvider {

    /**
     * Singleton instance of the class
     */
    private static DateProvider instance = null;

    /**
     * Number of milliseconds in a day
     */
    private final int DAY_MS = 1000 * 60 * 60 * 24;

    /**
     * Singleton accessor
     * @return singleton of the DateProvider class
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Function to get the current date
     * @return current date
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Function to get the date ten days ago
     * @return date ten days ago
     */
    public Date tenDaysAgo() {
        return new Date(now().getTime() - (10 * DAY_MS));
    }
}
