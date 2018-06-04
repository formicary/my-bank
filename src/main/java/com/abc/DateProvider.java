package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * This is a class for DateProvider.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class DateProvider {

    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    /**
     * This method returns the date of the instance.
     * @return The date of the instance.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

}
