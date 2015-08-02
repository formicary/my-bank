package com.abc;

import java.util.Calendar;
import java.util.Date;

public enum DateProvider {
    INSTANCE;

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
