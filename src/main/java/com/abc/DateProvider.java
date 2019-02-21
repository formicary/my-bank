package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    // No point in instantiating a DateProvider obj - replacing default ctor with a custom private one
    private DateProvider(){}

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
