package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SavingsAccountTest {

	private static final double DOUBLE_DELTA = 1e-15;
	SavingsAccount savingsAccount = new SavingsAccount();
	
	@Test
	public void interestEarnedSavingsAccount1() {
		
		savingsAccount.deposit(50);
		assertEquals(0.05, savingsAccount.interestEarned(), DOUBLE_DELTA);
	}
	
	@Test
	public void interestEarnedSavingsAccount2() {
		
		savingsAccount.deposit(1001);
		assertEquals(1.002, savingsAccount.interestEarned(), DOUBLE_DELTA);
	}
}
