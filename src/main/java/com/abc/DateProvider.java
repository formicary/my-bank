package com.abc;

import java.util.Calendar;
import java.util.Date;

class DateProvider {
    private static DateProvider instance = null;

    static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    Date now() {
        return Calendar.getInstance().getTime();
    }

    boolean tenDayCheck(Date a){
        long minus10days = now().getTime() - 10 * 24 * 60 * 60 * 1000;
        System.out.println("ten days ago:" + minus10days);
        System.out.println("compare date:" + a.getTime());

        return a.getTime() < minus10days;
    }
}
