package com.abc;

import java.time.LocalDateTime;

public class DateProvider {

    private static DateProvider instance = null;

    private DateProvider() {

    }

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
