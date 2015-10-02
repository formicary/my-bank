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
    
    @SuppressWarnings("deprecation")
	public int day(){
    	return Calendar.getInstance().getTime().getDay();
    }
    
    @SuppressWarnings("deprecation")
	public int month(){
    	return Calendar.getInstance().getTime().getMonth();
    }
}
