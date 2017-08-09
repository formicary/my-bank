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
    
    public long howManyDaysBeforeToday(Date olderDay){
    	long days =daysAppart(this.now(), olderDay);
    	return days;
    }

	public long daysAppart(Date newerDay, Date olderDay) {
		if(newerDay.before(olderDay)){
			Date temp = olderDay;
			olderDay = newerDay;
			newerDay = temp;
		}
		long diffInMillies = newerDay.getTime() - olderDay.getTime();
	    return diffInMillies / (24 * 60 * 60 * 1000); //Days
	}
}
