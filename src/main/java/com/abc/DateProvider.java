package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;
    public static final long dayInMillis = 86400000;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public static void setInstance(DateProvider instance) {
        DateProvider.instance = instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public Date getPreviousDay(int daysToSetDateBack){
        return new Date(Calendar.getInstance().getTimeInMillis() - (dayInMillis * daysToSetDateBack));
    }

}
