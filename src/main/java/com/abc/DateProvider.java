package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class DateProvider implements IDateProvider {
    
	private static Calendar currentdate = new GregorianCalendar();

    //Return the current date from the system
	public Calendar getcurrentdate(){
        return currentdate;
    }
    
    //Get the number of days between two dates
    public double daydifference(Calendar date1, Calendar date2){
    	long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
    	double daydiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    	return daydiff;
    }
    
    //Method to compare if one date is after the other
	public boolean ifdateafter(Calendar date1, Calendar date2){
		return date1.after(date2);
	}


    
    
    
}
