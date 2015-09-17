package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void testTransactions() {
		Account a = new Account(Account.SAVINGS);
		a.deposit(100.5);
		a.withdraw(50.5);
		double balance = a.sumTransactions();
		
		assertEquals(50.0, balance, DOUBLE_DELTA);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDepositIllegalArgumentException() {
		Account a = new Account(Account.SAVINGS);
		a.deposit(-2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWithdrawIllegalArgumentException() {
		Account a = new Account(Account.SAVINGS);
		a.withdraw(-2);
	}
	
	@Test
	public void checkAccountActivityDeposit() {
		Account a = new Account(Account.CHECKING);
		a.deposit(10);
		assertTrue(a.accountInactive());
	}
	
	@Test
	public void checkAccountActivityWithdrawal() {
		Account a = new Account(Account.CHECKING);
		a.deposit(10);
		a.withdraw(5);
		assertFalse(a.accountInactive());
	}
}
