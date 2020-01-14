package com.abc;

import java.util.Calendar;
import java.util.Date;


public class DateProvider {

    public DateProvider() {
        super();
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
