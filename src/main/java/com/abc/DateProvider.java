package com.abc;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;

public class DateProvider {
    private static DateProvider instance = null;
    private Clock clock = Clock.systemDefaultZone();

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
    
    // set the date to next day - used for testing
    public void nextDay() {
    	clock = Clock.offset(clock, Duration.ofDays(1));
    }
    
    public LocalDate now() {
    	return LocalDate.now(clock);
    }
}