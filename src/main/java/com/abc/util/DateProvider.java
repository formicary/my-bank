package com.abc.util;

import java.util.Calendar;
import java.util.Date;

public class DateProvider implements IDateProvider {
    private static DateProvider instance = null;

    private DateProvider(){};

    public static DateProvider getInstance(){
        if(instance == null){
            instance = new DateProvider();
        }

        return instance;
    }

     public Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
}
