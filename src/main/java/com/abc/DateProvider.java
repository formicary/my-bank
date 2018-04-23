package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    public static Date now() {
    	
        return Calendar.getInstance().getTime();
    }
    
    public static Date setDate(int year, int month, int day, int hour, int minutes, int seconds) {
  	
    	//calendar documentation: year is counted from 1900
    	Calendar cal = Calendar.getInstance();
		cal.set(year - 1900, month, day, hour, minutes, seconds);
		Date date = cal.getTime();
		
		return date;
    }
}
