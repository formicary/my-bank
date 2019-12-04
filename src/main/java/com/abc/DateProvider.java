package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;
    private Date customDate = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public void setCustomDate(Date date) {
        customDate = date;
    }

    public Date now() {
        if (customDate != null) {
            return customDate;
        } else {
            return Calendar.getInstance().getTime();
        }
        
    }
}