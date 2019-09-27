package com.abc;

import java.util.Calendar;
import java.util.Date;

class DateProvider {
    private static DateProvider instance = null;

    static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    Date now() {
        return Calendar.getInstance().getTime();
    }
}
