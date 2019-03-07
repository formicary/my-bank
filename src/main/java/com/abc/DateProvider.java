package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * The Class DateProvider.
 */
public class DateProvider {
    private static DateProvider instance = null;

    /**
     * Gets the single instance of DateProvider.
     *
     * @return single instance of DateProvider
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Gets the date at the current time.
     *
     * @return the date
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

}
