package com.accenture.mybank.utils;

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

    /**
     * This method calculates the difference in days between 2 dates
     * @param start
     * @param end
     * @param locale
     * @return days difference
     */
    public long calculateDifferenceInDays(Date start, Date end, Locale locale) {
        Calendar cal;

        cal = defaultDate(start, locale);
        long startTime = cal.getTimeInMillis();

        cal = defaultDate(end, locale);
        long endTime = cal.getTimeInMillis();
        TimeZone timezone = cal.getTimeZone();
        int offsetStart = timezone.getOffset(startTime);
        int offsetEnd = timezone.getOffset(endTime);
        int offset = offsetEnd - offsetStart;

        return TimeUnit.MILLISECONDS.toDays(endTime - startTime + offset);
    }

    private Calendar defaultDate(Date date, Locale locale){
        Calendar calendar = Calendar.getInstance(locale);

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }
}