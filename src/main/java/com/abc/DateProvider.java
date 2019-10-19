package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    /**
     * DateProvider class constructor. Initialises instance variable
     * @return instance
     */
    public static DateProvider getInstance() {
        if (instance == null) {
            instance = new DateProvider();
        }
        return instance;
    }

    /**
     * Returns the current date at the time of instance initialisation
     * @return Date 
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
