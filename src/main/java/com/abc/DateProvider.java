package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    
    public static long getDateDiff(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
