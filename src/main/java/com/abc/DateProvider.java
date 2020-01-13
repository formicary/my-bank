package com.abc;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateProvider {
    private static DateProvider instance = null;
    public Date startingDate;

    /**
     * Sets a startingDate. Used for testing
     * @param newDate
     */
    public void setStartingDate(Date newDate){
        startingDate = new Date(newDate.getTime());
    }

    public static DateProvider getInstance() {
        if (instance == null){
            instance = new DateProvider();
            instance.startingDate = instance.now();
        }

        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public int getYearDays(){
        return 365;
    }

    public static int getDayDifference(Date dateFrom, Date dateTo){

        long diffMilis = Math.abs(dateFrom.getTime() - dateTo.getTime());
        int daysElapsed = (int)TimeUnit.DAYS.convert(diffMilis, TimeUnit.MILLISECONDS);
        return daysElapsed;
    }


    
}


