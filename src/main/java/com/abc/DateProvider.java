package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Date provider provides a method to get the current system date.
 * @author Donald Campbell (campbell.donald@gmail.com)
 *
 */
public class DateProvider {
    private static DateProvider instance = null;

    /**
     * Returns an instance of the DateProvider class.
     * @return An instance of the DateProvider class.
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        
        return instance;
    }

    /** 
     * Returns a Date object representing the current system time.
     * @return A Date object representing the current system time.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
