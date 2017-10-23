package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.util.Date;

/**
 * Test cases for the dateProvider class.
 * 
 * @author Christopher J. Smith
 */
public class DateProviderTest {

	@Test
	/**
	 * Tests that this is a singleton class
	 */
	public void singletonTest() {
		DateProvider instance = DateProvider.getInstance();
		assertSame(instance, DateProvider.getInstance());
	}

	@Test
	/**
	 * Check that a valid date is returned each call
	 */
	public void validDate() {
		Date d1 = DateProvider.getInstance().now();

		try {
			Thread.sleep(2000);
		} catch (Exception e) {

		}

		Date d2 = DateProvider.getInstance().now();

		assertTrue(d1 instanceof Date && d1.compareTo(d2) < 0);
	}
}
