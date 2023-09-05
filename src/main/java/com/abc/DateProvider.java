package com.abc;

import java.time.LocalDate;
import java.util.Objects;

public class DateProvider {

    private static DateProvider instance = null;
    private static LocalDate forcedDate = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    static void setForcedDate(LocalDate forcedDate) {
        DateProvider.forcedDate = forcedDate;
    }

    public LocalDate now() {
        return Objects.requireNonNullElseGet(forcedDate, LocalDate::now);
    }

}
