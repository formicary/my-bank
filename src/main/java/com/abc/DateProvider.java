package com.abc;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

public class DateProvider {
    private static DateProvider instance = null;
    private Clock clock = Clock.systemDefaultZone();
    

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }
    
    
    // this method is for testing the maxi savings account interest to see if interest rate changes if no withdrawals for 10 days.
    public void addFifthteenDays() {
    	clock = Clock.offset(clock, Duration.ofDays(15));
    }
    
    
    
   
   
}
