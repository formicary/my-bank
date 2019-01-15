package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    public static Date now() {
        return Calendar.getInstance().getTime();
    }

    public static Date generateDate(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

}
