package com.abc;

import java.util.Calendar;
import java.util.Date;

// What's the purpose of this class?
public class DateProvider {
    // Can the overall class just not be static is we're doing it this way?
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
