package com.abc;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
    public Date createDate (int day, int month, int year) {
        Instant createDate = LocalDate.of(year, month, day).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(createDate);
    }

    public Date inTenDaysPast() {
        return Date.from(LocalDate.now().minusDays(10).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date oneDayAgo() {
        return Date.from(LocalDate.now().minusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
