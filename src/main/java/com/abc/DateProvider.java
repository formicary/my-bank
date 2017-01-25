package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

	public static int getDayDifference(Date d1, Date d2) {
		long diff = Math.abs(d2.getTime() - d1.getTime());
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
}
