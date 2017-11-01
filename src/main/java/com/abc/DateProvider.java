package com.abc;

//Imports
import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	//Variable initialising a class instance to null
    private static DateProvider instance = null;

    //Method to return a DateProvider instance if there isn't one already
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    //Method to return the current time
    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    //Method to return the number of days, whether it is a leap year or not
    public static int daysInYear() {
    	Calendar calendar = Calendar.getInstance();
    	return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }
}
