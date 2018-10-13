package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class DateProvider {
    private static DateProvider instance = null;
    
    /**
     * 
     * @return the singleton date provider
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
    
    /**
     * 
     * @return current date
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    /**
     * Get difference between current date and any given date in seconds
     * @param date the date to compare with
     * @return the difference in seconds
     */
    public long getDifference(Date date) {
    	long diff = (now().getTime() - date.getTime())/1000;
    	return diff;
    }
}
