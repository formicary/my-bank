package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    public static Date now() {
        return Calendar.getInstance().getTime();
    }
}
