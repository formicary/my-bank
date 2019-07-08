package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
    
    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public int daysBetweenTwoDates(Date firstDate, Date secondDate) {
     
        long differenceInMSecs = Math.abs(secondDate.getTime() - firstDate.getTime());
        int differenceInDays = (int) TimeUnit.DAYS.convert(differenceInMSecs, TimeUnit.MILLISECONDS);
    	
    	return differenceInDays;
    }
}
