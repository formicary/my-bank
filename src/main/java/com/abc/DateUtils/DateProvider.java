package com.abc.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    /**
     * @return DateProvider instance
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * 
     * @return current time (Date)
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

}
