package com.abc;

import java.util.Calendar;
import java.util.Date;

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
    
    public Date oneYearAgo() {
    	Date today = now();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(today);
    	cal.add(Calendar.YEAR, -1);
    	return cal.getTime();
    }
    
    public Date incrementByDay(Date dateToIncrement) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(dateToIncrement);
    	cal.add(Calendar.DAY_OF_MONTH, 1);
    	return cal.getTime();
    }
}
