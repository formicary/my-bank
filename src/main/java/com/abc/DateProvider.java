package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Class that handles dates
 */
public class DateProvider {
    private static DateProvider instance = null; //Singleton instance of the class

    /**
     * Constructor to prevent creation of another singleton instance
     */
    private DateProvider(){}

    /**
     * Function to access singleton instance
     * @return singleton instance of DateProvider class
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Function that gets current date
     * @return current date
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
