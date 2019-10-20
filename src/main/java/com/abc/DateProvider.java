package com.abc;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;

/*
public final class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
	private 

    public static Calendar now() {
        return Calendar.getInstance();
    }
    
    // Get date n days from now (future or past), for testing purposes only
    public static Calendar nDaysAway(int daysShifted) {
    	Calendar shiftedCal = Calendar.getInstance();
    	shiftedCal.add(Calendar.DAY_OF_YEAR, daysShifted);
    	return shiftedCal;
    }
    
    public static long daysBetween(Calendar startDate, Calendar endDate) {
    	return ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    }
}
*/

// DateProvider class made for testing, for real use we would create a version of this
// which returns the actual date, rather than one we have selected.
public class DateProvider {
	private static DateProvider instance = null;
	private Calendar date;	
	
	public DateProvider() {
		date = Calendar.getInstance();
	}
	
    public static DateProvider getInstance() {
        if (instance == null) {
        	instance = new DateProvider();
        }
        return instance;
    }
    
	public Calendar now() {
		date = (Calendar) date.clone();
		return date;
	}
	
    public static long daysBetween(Calendar startDate, Calendar endDate) {
    	return ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    }
	
	// advance allows for manual selection of the date
	public void advanceDate(int days) {
		date = (Calendar) date.clone();
		date.add(Calendar.DAY_OF_YEAR, days);
	}
}



