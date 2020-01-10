package com.abc;

import java.util.Date;

public class DateProvider {
	public final long DAYS = 1000 * 60 * 60 * 24; // A day in milliseconds
    private static DateProvider instance = null;
    
    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }

    public Date now() {
        return (new Date());	// new Date() already gets current time
    }
}
