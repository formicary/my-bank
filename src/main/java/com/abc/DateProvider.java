package com.abc;

import java.time.Clock;
import java.time.LocalDate;

public class DateProvider {
    private static Clock clock = Clock.systemDefaultZone();

    public static LocalDate now() {
        return LocalDate.now(DateProvider.clock);
    }

    //FOR TESTING ONLY
    public static void setClock(final Clock clock) {
        DateProvider.clock = clock;
    }
}
