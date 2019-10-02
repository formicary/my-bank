package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CheckingTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void testCheckingInterest() {
		Account checking = new Checking();
		assertEquals(0.0, checking.interestEarned(), DOUBLE_DELTA);
		checking.deposit(200);
		assertEquals(0.2, checking.interestEarned(), DOUBLE_DELTA);
	}
}
