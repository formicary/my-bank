package com.abc;

import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import java.util.Date;

public class DateProvider {

    private static DateProvider instance = null;
    private Calendar cal;

    public DateProvider() {
        this.cal = Calendar.getInstance();
    }

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return cal.getTime();
    }

    public void add(int field, int amount) {
        cal.add(field, amount);
    }

    public void reset() {
        cal.setTime(Calendar.getInstance().getTime());
    }

    public int daysToNow(Date date) {

        long diff = this.now().getTime() - date.getTime();
        int days = (int) TimeUnit.MILLISECONDS.toDays(diff);

        return days;
    }
}
