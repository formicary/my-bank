package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;
    private final int DAY_MS = 1000 * 60 * 60 * 24;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public Date tenDaysAgo() {
        return new Date(now().getTime() - (10 * DAY_MS));
    }
}
