package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.PrimitiveIterator.OfDouble;

import javax.xml.crypto.Data;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null) {
            instance = new DateProvider();
        }
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
    
    public static int daysDiff(Date last, Date penultimate) {
    	Long difference = last.getTime() - penultimate.getTime();
    	int numberOfDays = (int) (difference/86400000);
    	return numberOfDays;
    }
 
}
