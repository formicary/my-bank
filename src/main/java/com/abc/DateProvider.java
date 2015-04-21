package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static Date date;

    public static Date now() {
        //Set date as either current date, or fake date
        return (date != null ? date : Calendar.getInstance().getTime());
    }

    public static void setDate(Date newDate) {
        //Allow for JUnit to create fake date to test with
        date = newDate;
    }

    public static void resetDate() {
        date = null;
    }
}
