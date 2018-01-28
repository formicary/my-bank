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

    /**
     * This will return a static instance ofDateProvider to be used by the caller
     * @return A static instance of DateProvider
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * This function returns a Date object that is set 10 days before the time this function was called.
     * @return  A Date object that is set 10 days before the time this function was called.
     */
    public Date getTenDaysAgo() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, -10);
        return cal.getTime();
    }

    /**
     * This function returns a Date object for the current time.
     * @return  A Date object set to the current time.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

}