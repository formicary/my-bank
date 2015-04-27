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

    // Calculate the daily interests
    public long daysBetween(Date dayOne, Date dayTwo) {
    	long days = dayTwo.getTime() - dayOne.getTime();
    	days /= (24 * 60 * 60 * 1000);
    	return days;
    }
}
