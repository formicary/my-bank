package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    /**
     * GetInstance method creates an instance of the DateProvider if there's none, and returns the instance if there is one.
     * @return Returns the instance of the DateProvider.
     */
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * Now method returns the Datetime as of this moment.
     * @return Returns the Datetime as of this moment.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * GetDateFromPast gets the date from the number of days before the current date.
     * @param days The number of days before the current date.
     * @return Returns the date from the number of days before  the current date
     */
    public Date getDateFromPast(int days){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }
}
