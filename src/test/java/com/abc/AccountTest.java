package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class AccountTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	CheckingAccount checkingAccount = new CheckingAccount();

	@Test(expected = IllegalArgumentException.class)
	public void negativeDepositTest() {
		checkingAccount.deposit(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeWithdrawalTest() {
		checkingAccount.withdraw(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void withdrawalNotFeasible() {
		checkingAccount.deposit(50);
		checkingAccount.withdraw(500);
	}
}

