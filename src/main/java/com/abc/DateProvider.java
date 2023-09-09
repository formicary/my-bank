package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * A utility class for providing date and time-related functionality.
 * This class follows the Singleton pattern to ensure a single instance.
 */
public class DateProvider {

    /**
     * The single instance of the DateProvider class.
     */
    private static final DateProvider instance = new DateProvider();

    /**
     * Private constructor to prevent external instantiation.
     */
    private DateProvider() {
        // This constructor is intentionally private.
    }

    /**
     * Retrieves the single instance of the DateProvider class.
     *
     * @return The DateProvider instance.
     */
    public static DateProvider getInstance() {
        return instance;
    }

    /**
     * Gets the current date and time.
     *
     * @return A Date object representing the current date and time.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
