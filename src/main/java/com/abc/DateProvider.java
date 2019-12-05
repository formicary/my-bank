package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Utility class used by all other classes which require the specific time,
 * useful for testing since it allows the time to be overridden to simulate time passing
 */
public class DateProvider {
    /**
     * The class instance
     */
    private static DateProvider instance = null;

    /**
     * A custom set date to be given to all code that asks for the current date
     */
    private Date customDate = null;

    /**
     * provides an object that can be called to find the 'current' time,
     * creating a new object if one hasn't been initialised yet
     * @return the object
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Allows a custom date to be set which overrides providing the actual current date
     * when asked for it
     * @param date the date to be given to requests
     */
    public void setCustomDate(Date date) {
        customDate = date;
    }

    /**
     * Gives either the current time, or the custom override time if it has been set
     * @return the current date
     */
    public Date now() {
        if (customDate != null) {
            return customDate;
        } else {
            return Calendar.getInstance().getTime();
        }
        
    }
}