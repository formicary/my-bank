package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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


    public long calculateDifferenceInDays(Date start, Date end, Locale locale) {
        Calendar cal;

        cal = setToDefault(start, locale);
        long startTime = cal.getTimeInMillis();

        cal = setToDefault(end, locale);
        long endTime = cal.getTimeInMillis();

        // calculate the offset if one of the dates is in summer time and the other one in winter time
        TimeZone timezone = cal.getTimeZone();
        int offsetStart = timezone.getOffset(startTime);
        int offsetEnd = timezone.getOffset(endTime);
        int offset = offsetEnd - offsetStart;

        return TimeUnit.MILLISECONDS.toDays(endTime - startTime + offset);
    }

    private Calendar setToDefault(Date date, Locale locale){
        Calendar calendar = Calendar.getInstance(locale);

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }
}
