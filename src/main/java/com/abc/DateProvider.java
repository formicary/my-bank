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
    
    public Date tenDaysPrior() {
    	Calendar currentTime = Calendar.getInstance();
    	currentTime.add(Calendar.DAY_OF_MONTH, -10);
        return currentTime.getTime();
    }
}
