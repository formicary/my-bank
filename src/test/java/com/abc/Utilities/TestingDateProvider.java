package com.abc.Utilities;

import com.abc.DateProvider;

import java.util.Calendar;
import java.util.Date;

public class TestingDateProvider extends DateProvider {
    private static TestingDateProvider instance = null;
    private boolean testingMode = false;
    private long currentDay;

    public static TestingDateProvider getInstance() {
        if (instance == null)
            instance = new TestingDateProvider();
        return instance;
    }

    public Date now() {
        if(!testingMode) {
            return Calendar.getInstance().getTime();
        } else {
            Date date = new Date(currentDay);
            currentDay += dayInMillis;
            return date;
        }
    }

    public Date getPreviousDay(int daysToSetDateBack){
        return new Date(Calendar.getInstance().getTimeInMillis() - (dayInMillis * daysToSetDateBack));
    }

    public void setTestingModeOn(int daysToSetDateBack){
        testingMode = true;
        currentDay = Calendar.getInstance().getTimeInMillis() - (dayInMillis * daysToSetDateBack);
    }

    public void setTestingModeOff(){
        testingMode = false;
    }


}
