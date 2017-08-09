package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class DateProviderTest {

	private static final double DOUBLE_DELTA = 1e-10;

	@Test
	public void daysAppartTest() {
		DateProvider dp = new DateProvider();

		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, -15);
		java.util.Date d2 = cal2.getTime();

		long diff = dp.daysAppart(d2, dp.now());
		assertEquals(diff, 15, DOUBLE_DELTA);

	}

}
