package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider dp = null;

    public static DateProvider getInstance() {
        if (dp == null)
            dp = new DateProvider();
        return dp;
    }

    public Date now() {

        return Calendar.getInstance().getTime();
    }
}
