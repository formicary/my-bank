package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    // gets the difference in days between two dates
    public static long daysDifference(Date date, Date date1){

        long diff = date.getTime() - date1.getTime();

        return Math.abs(diff / (1000*60*60*24));
    }


}

