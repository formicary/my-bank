package com.abc;

import java.util.Calendar;
import java.util.Date;

public final class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public int differentDays(Date from, Date to)
    {
        int days = (int) (diff/(1000 * 60 * 60 * 24));
        return days;

    }
}
