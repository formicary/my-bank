package com.abc.MainClasses;

import java.util.Calendar;
import java.util.Date;

import com.abc.Interfaces.DateProviderInterface;

public class DateProvider implements DateProviderInterface{
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}