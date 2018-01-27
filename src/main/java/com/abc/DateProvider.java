package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is a static object that exists to provide the Date to timestamp Transactions.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date getTenDaysAgo(){
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -10);
        return cal.getTime();
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
