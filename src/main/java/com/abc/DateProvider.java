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

    public double daysThisYear() {
        if ((year() % 4 == 0)) {
            return 366;
        }
        else {
            return 365;
        }
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public int year() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }
}
