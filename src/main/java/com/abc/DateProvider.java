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
    
    public static boolean olderThanTenDays(Date date) {
    	if(date == null) {
    		return false;
    	}
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(DateProvider.getInstance().now());
    	calendar.add(Calendar.DAY_OF_YEAR, -10);
    	Date beforeTenDays = calendar.getTime();
    	
    	return date.before(beforeTenDays);
    }
    
    public static double CalculateDailyCompound(double comp) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());

    	int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    	System.out.println(numOfDays);
    	return comp / numOfDays;
    }
}
