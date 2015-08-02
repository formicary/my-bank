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
    
    //Find the day x days ago from today
    public Date daysAgo(int days) {
    	Calendar oldDay = Calendar.getInstance();
    	oldDay.add(Calendar.DATE, -days);
    	return oldDay.getTime();    	
    }
}
