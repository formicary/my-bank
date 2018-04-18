package com.abc.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Exposes methods to get the current date.
 */
public class DateProvider implements IDateProvider {
    /**
     * The instance of this class.
     */
    private static final DateProvider instance = new DateProvider();

    /**
     * Returns the single instance of this DateProvider class.
     *
     * @return The single instance of this DateProvider class.
     */
    public static DateProvider getInstance() {
        return instance;
    }

    /**
     * Returns the present date.
     *
     * @return The present date.
     */
    public Date now() {
        return Calendar.getInstance().getTime();
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[DateProvider: instance=%s]", this);
    }
}
