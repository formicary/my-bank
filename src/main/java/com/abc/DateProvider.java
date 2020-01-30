package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;


public class DateProvider {
    
	private static DateProvider instance = null;

    //Create a DateProvider.
	public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
	
	//Get current time.
    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    //Get current time as LocalDate.
    public LocalDate getDateNoTime() {
    	return LocalDate.now();
    }
    
    //Get date of 10th day before current date. 
    public LocalDate minusTen(LocalDate d) {
    	return d.minusDays(10);
    }
    
    //Convert Date to LocalDate.
    public LocalDate toLocalDate(Date d) {
    	return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
}
