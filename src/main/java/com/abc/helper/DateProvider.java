package com.abc.helper;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Static class that returns the current Date
 */
public class DateProvider {

    // representing the number of milliseconds in a day, used for offsetting Dates
    private static final long DAYS = 1000 * 60 * 60 * 24;

    /**
     * Returns the current Date
     * @return the Calendar's Date instance
     */
    public static Date now() {
        return Calendar.getInstance().getTime();
    }


    /**
     * Returns a copy of a given date, offset by a number of days
     * @param date the date to apply the offset to
     * @param days the number of days to offset the date parameter by
     * @return a Date instance, which is offset from the passed date parameter
     */
    public static Date offset(Date date, long days) {
        return new Date(date.getTime() + (days * DAYS));
    }

    /**
     * Returns the current Date, with an offset in the number of days
     * @param days the amount of days to offset the current Date by
     * @return a Date Object for the current time, offset by days
     */
    public static Date offset(long days) {
        return new Date(now().getTime() + (days * DAYS));
    }

    /**
     * Returns a List of chronological days, leading to the current Date
     * @param days the number of days to generate a chronology up to
     * @return a List of Date Objects in chronological order
     */
    public static List<Date> buildChronology(long days) throws InvalidArgumentException {

        if (days <= 0) throw new InvalidArgumentException(new String[]{"days parameter must be positive"});
        List<Date> chronology = new ArrayList<>();
        for (long i = days; i >= 0; i--) chronology.add(offset(-i));
        return chronology;
    }

    public static List<Date> buildChronology(Date start, Date end){
        long offset = getOffset(start, end);


        List<Date> chronology = new ArrayList<>();
        for (long i = offset; i != 0; i += offset < 0 ? -1 : 1) chronology.add(offset(-i));
        return chronology;

    }



    /**
     * Returns the difference, in days, between two dates
     * @param a the start Date
     * @param b the end Date
     * @return the number of days between
     */
    public static long getOffset(Date a, Date b) {
        return TimeUnit.DAYS.convert((b.getTime() - a.getTime()), TimeUnit.MILLISECONDS);

    }

}
