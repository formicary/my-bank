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
    
    /**
     * Method to return Date of now
     * @return
     * 		Date: date of now/Current date.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
	/**
	 * Method to return Date of days before now.
	 * @param days
	 * 		int: number of days before now
	 * @return
	 * 		Date: the date days before now.
	 */
	public Date datePast(int days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }
}
