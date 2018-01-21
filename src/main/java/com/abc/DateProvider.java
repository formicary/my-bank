package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * This is a static object that exists to provide the Date to timestamp Transactions.
 * @author Matthew Howard
 */

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    //TODO: should this be static?
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
