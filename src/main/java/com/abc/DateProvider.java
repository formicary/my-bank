package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that uses Calender to provide each Transaction with the Date their were created
 * 
 * @author Accenture
 *
 */
public class DateProvider {
    private static DateProvider instance = null;

    /**
     * Method that checks if an instance has been created, and if not creates a new instance
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Method that returns the current Date
     * @return Date when the method was called
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
