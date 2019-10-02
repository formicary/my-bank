package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MaxiSavingsTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void testMaxiSavingsInterest() {
		Account maxiSavings = new MaxiSavings();
		assertEquals(0.0, maxiSavings.interestEarned(), DOUBLE_DELTA);
		maxiSavings.deposit(200);
		assertEquals(4.0, maxiSavings.interestEarned(), DOUBLE_DELTA);
		maxiSavings.deposit(1300);
		assertEquals(45.0, maxiSavings.interestEarned(), DOUBLE_DELTA);
		maxiSavings.deposit(1000);
		assertEquals(120.0, maxiSavings.interestEarned(), DOUBLE_DELTA);
	}
}
