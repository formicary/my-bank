package com.abc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    //Return a new date which is provided date + provided amount of days to add
    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, +days);
        return cal.getTime();
    }

    //Used for testing purposes to create assign dates going back in time
    public Date daysAgo(int days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    //Return the amount of days between 2 dates
    public int daysBetween(Date d1, Date d2) {
        long difference = d2.getTime() - d1.getTime();
        return (int)(difference / (1000*60*60*24));
    }

    //Return a date in dd/MM/yyyy format for statement
    public String stringify(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);

    }
}