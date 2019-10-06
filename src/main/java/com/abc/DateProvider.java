package com.abc;

import java.time.ZoneId;
import java.time.LocalDate;

public class DateProvider {
    // Without further information on the bank's operations, currently coded as if based in the UK - GMT used.
    public static LocalDate now() {
        return LocalDate.now(ZoneId.of("GMT"));
    }
}
