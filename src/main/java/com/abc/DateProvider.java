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

    public boolean tenDayCheck(Date a){
        long day10 = 0 ;
        day10 = 10 * 24 * 60 * 60 * 1000;
        boolean olderThan10 = now().getTime() < ((a.getTime())+day10);
        return olderThan10;
    }
}
