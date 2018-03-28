package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    //Returns the DateProvider instance
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    //Returns the date and time of now
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
