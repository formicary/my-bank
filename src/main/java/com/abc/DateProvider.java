package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    public static double MEAN_YEAR = 365.25;

    private DateProvider()
    {
    	return;
    }
    public static Date now() {
        return Calendar.getInstance().getTime();
    }
    public static final double getDifferenceDays(Date from, Date to)
    {
    	double difference = (to.getTime() - from.getTime())/86400000.0;
    	return difference;
    }
    public static final Date getStartOfDay(Date date) 
    {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	return cal.getTime();
    }
    public static final Date getDateWithOffset(Date date, double daysOffset)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	cal.setTimeInMillis( cal.getTimeInMillis() + (long)(daysOffset * 86400000) );
		return cal.getTime();
    }
}
