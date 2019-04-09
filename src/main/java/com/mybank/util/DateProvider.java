package com.mybank.util;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    private static DateProvider instance = null;
    private Calendar calendarInstance = null;

    private DateProvider() {
        calendarInstance = Calendar.getInstance();
    }

    /**
     *
     * @return
     */
    public static DateProvider getInstance() {
        if (instance == null) {
            instance = new DateProvider();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public Date now() {
        return calendarInstance.getTime();
    }
}
