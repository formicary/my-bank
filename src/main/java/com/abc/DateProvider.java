package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * DateProvider class provides dates
 * @author Matthew Mukalere
 *
 */
public class DateProvider {
    private static DateProvider instance = null;

    /**
     * DateProvider singleton constructor.
     * @return instance Single instance of the DateProvider
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
    
    /**
     * Gets the current time.
     * @return now The current time
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
