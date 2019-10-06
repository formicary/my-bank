package com.abc;

import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;

public class DateProvider {//TODO MAKE THIS JUST A DATE, ADD A NOTE SAYING i KNOW THIS LIMITS IT INTERNATIONALLY?
    public static LocalDate now() {
        return LocalDate.now(ZoneId.of("GMT"));
    }
}
