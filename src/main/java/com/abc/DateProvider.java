package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    
    public static long differenceBetweenDays(Date first, Date second) {
    	long msDiff = Math.abs(first.getTime() - second.getTime());
		long daysDiff = TimeUnit.DAYS.convert(msDiff, TimeUnit.MILLISECONDS);
		
		return daysDiff;
    }
}
