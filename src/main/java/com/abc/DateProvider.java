package com.abc;

import java.util.Calendar;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Calendar now() {
        return Calendar.getInstance();
    }
}
