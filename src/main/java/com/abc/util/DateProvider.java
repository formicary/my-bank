package com.abc.util;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    private static DateProvider instance = null;
    private Calendar calendar;

    public static synchronized DateProvider getInstance() {
        if (instance == null) {
            instance = new DateProvider();
        }

        return instance;
    }

    public Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public Date addDaysToCurrentDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public Date getCurrentDateBefore10Days() {
        return addDaysToCurrentDate(-10);
    }
}
