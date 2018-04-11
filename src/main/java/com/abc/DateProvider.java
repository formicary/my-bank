package com.abc;

import java.time.LocalDateTime;

public final class DateProvider {

    private DateProvider() {
    }

    public static LocalDateTime getNow(){
        return LocalDateTime.now();
    }
    // day is day of month
    public static LocalDateTime getNow(int month, int day, int hour, int minute){
        return LocalDateTime.of(2018, month, day, hour, minute);
    }
}
