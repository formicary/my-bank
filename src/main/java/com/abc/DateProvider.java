package com.abc;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/** The class {@code DateProvider} is a singleton used for returning the date. */
public class DateProvider {

    private static DateProvider instance = null;

    /**
     * Get the instance of {@code DataProvider}, initialises an instance if one does not exist
     * already.
     *
     * @return the {@code DateProvider} instance
     */
    public static DateProvider getInstance() {
        if (instance == null) instance = new DateProvider();
        return instance;
    }

    /**
     * Get the current {@code LocalDateTime}.
     *
     * @return The current time.
     */
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}