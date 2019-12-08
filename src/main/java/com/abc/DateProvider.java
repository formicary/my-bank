package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that handles dates
 */
class DateProvider {

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
    static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Function to get the current date
     * @return current date
     */
    Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Function to get the date ten days ago
     * @return date ten days ago
     */
    Date daysAgo(int days) {
        return new Date(now().getTime() - (days * DAY_MS));
    }
}
