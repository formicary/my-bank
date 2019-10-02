package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test (expected = IllegalArgumentException.class)
	public void testDepositAmountBelowZero() {
		Account checking = new Checking();
		checking.deposit(-100);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testWithdrawAmountBelowZero() {
		Account checking = new Checking();
		checking.withdraw(-100);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testWithdrawAmountWhenNotEnoughBalance() {
		Account checking = new Checking();
		checking.withdraw(100);
	}
	@Test
	public void testValidWithdrawal() {
		Account checking = new Checking();
		checking.deposit(200);
		checking.withdraw(100);
		assertEquals(100,checking.getBalance(),DOUBLE_DELTA);
	}
}
