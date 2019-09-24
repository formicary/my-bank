package com.abc.util;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    //TODO may belong to separate directory completely as this is more a utility class
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
