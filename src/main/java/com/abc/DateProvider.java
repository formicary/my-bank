package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;
    private final long TEN_DAYS_MILLISECS = 864000000;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    //This method returns the date of 10 days prior at time 00:00:00
    public Date tenDaysAgo(){
        Date date = Calendar.getInstance().getTime();
        long todayMiliseconds = 1000*(date.getSeconds() + date.getMinutes()*60+date.getHours()*3600);
        date.setTime(date.getTime()-TEN_DAYS_MILLISECS-todayMiliseconds);
//        System.out.println(date);
        return date;
    }

}
