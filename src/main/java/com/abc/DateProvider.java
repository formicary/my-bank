package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
    // The instance creation and the non-static now method
    // seemed pointless so I changed it to a more direct method.
    public static Date getNow() {
        return Calendar.getInstance().getTime();
    }
}
