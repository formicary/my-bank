package com.abc;

import java.time.LocalDateTime;

public class DateProvider {
    private static DateProvider instance = null;

    // Singleton to ensure only one instance
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
