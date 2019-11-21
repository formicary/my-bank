package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * DateConstants singleton class. Provides a universal baseline for all transactions.
 * An epoch of sorts.
 */
class DateProvider {
    private static DateProvider instance = null;

    static DateProvider getInstance() {
        if (instance == null) instance = new DateProvider();
        return instance;
    }

    /**
     * @return the current time.
     */
    Date now() {
        return Calendar.getInstance().getTime();
    }
}
