package com.abc;

import java.util.Calendar;

public class CalendarHelper {
    public static void calendarToMidnight(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    public static long milisecondDifference(Calendar c1, Calendar c2) {
        return c1.getTimeInMillis() - c2.getTimeInMillis();
    }

    public static long dayDifference(Calendar c1, Calendar c2) {
        return milisecondDifference(c1, c2) / (24 * 60 * 60 * 1000);
    }

    public static Calendar now() {
        return Calendar.getInstance();
    }

    public static Calendar minimumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.MIN_VALUE);
        return calendar;
    }
}
