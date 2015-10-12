package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateProvider {
    private static DateProvider instance = null;
    private Calendar cal;

    public DateProvider() {
        Locale locale = Locale.ENGLISH;
        TimeZone tz = TimeZone.getTimeZone("GMT");
        cal = Calendar.getInstance(tz, locale);
    }

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return cal.getTime();
    }
}
