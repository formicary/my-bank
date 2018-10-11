package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Static class that returns the current Date
 */
class DateProvider {

    /**
     * Returns the current Date
     * @return the Calendar's Date instance
     */
    static Date now() {
        return Calendar.getInstance().getTime();
    }

}
