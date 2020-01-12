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
        //TODO: why do we need DateProvider if Calendar does all the work?
        return Calendar.getInstance().getTime();
    }

    //TODO: consider leap year, or make it an average 365.25
    public int getYearDays(){
        return 365;
    }

    public int getNumberOfElapsedDays(){

        long diffMilis = this.now().getTime() - startingDate.getTime();
        int daysElapsed = (int)TimeUnit.DAYS.convert(diffMilis, TimeUnit.MILLISECONDS);
        return daysElapsed;
    }


    
}


