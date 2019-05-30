package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider {
	private static DateProvider instance = null;

	public static DateProvider getInstance() {
		if (instance == null)
			instance = new DateProvider();
		return instance;
	}

	// IMPORTANT - Please use 'CurrentTime()' in deployment
	// 'TestOnlyCurrentTime()' is for testing only
	public Date now() {
		// return (new CurrentTime()).now();  
		return TestOnlyCurrentTime.getInstance().now();
	}
}
