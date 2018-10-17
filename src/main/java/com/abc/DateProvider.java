package com.abc;

import java.time.LocalDate;

public class DateProvider {
    private static DateProvider instance = null;

    static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    static LocalDate now() {
        return LocalDate.now();
    }

    public boolean compareOlderThan(LocalDate a, long days){
        return a.isBefore(LocalDate.now().minusDays(days));
    }

    public boolean checkSameDay(LocalDate date1, LocalDate date2){
        return date1.isEqual(date2);
    }
}

