package com.abc;


import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SavingsTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void testSavingsInterest() {
		Account savings = new Savings();
		assertEquals(0.0, savings.interestEarned(), DOUBLE_DELTA);
		savings.deposit(200);
		assertEquals(0.2, savings.interestEarned(), DOUBLE_DELTA);
		savings.deposit(1300);
		assertEquals(2.0, savings.interestEarned(), DOUBLE_DELTA);
	}
}
