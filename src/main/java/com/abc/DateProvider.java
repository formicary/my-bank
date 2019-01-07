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

	public Date now() {
		return Calendar.getInstance().getTime();
	}

	public Date tenDaysBeforeCurrentDate() {
		Date currentDate = DateProvider.getInstance().now();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.DATE, -10);
		Date tenDaysBefore = cal.getTime();
		return tenDaysBefore;
	}
}
