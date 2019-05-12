/*Edited by: Foyaz Hasnath*/
package com.abc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }

    public String dateFormat(Date dateToConvert){
        final String formattedDate = new SimpleDateFormat("dd-MM-yyyy").format(dateToConvert);
        return formattedDate;
    }

}
