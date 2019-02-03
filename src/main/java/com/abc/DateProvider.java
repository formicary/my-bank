package com.abc;

import java.time.LocalDate; // changed all methods to implement new up to data Java Date class

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public LocalDate now() {
        return LocalDate.now();
    }
}
