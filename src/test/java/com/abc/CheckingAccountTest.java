package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CheckingAccountTest {

	private static final double DOUBLE_DELTA = 1e-15;
	CheckingAccount checkingAccount = new CheckingAccount();

	@Test
	public void interestEarnedTest() {
		
		checkingAccount.deposit(50);
		assertEquals(0.05, checkingAccount.interestEarned(), DOUBLE_DELTA);
	}

}
