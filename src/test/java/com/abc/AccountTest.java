package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void testForRecentWithdrawal() {
		Account savings_one = new Account(Account.SAVINGS);
		savings_one.deposit(500.0);
		savings_one.withdraw(100.0);
		Account savings_two = new Account(Account.SAVINGS);
		savings_two.deposit(4.0);
		assertEquals(true, savings_one.withdrawalsDone());
		assertEquals(false, savings_two.withdrawalsDone());
	}
	
	@Test
	public void testCorrectBalance() {
		Account checkingAccount = new Account(Account.CHECKING);
		checkingAccount.deposit(100.0);
		checkingAccount.withdraw(30.0);
		assertEquals(70.0, checkingAccount.getBalance(), DOUBLE_DELTA);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBadDeposit() {
		Account savings = new Account(Account.SAVINGS);
		savings.deposit(-4.0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testBadWithdrawal() {
		Account savings = new Account(Account.SAVINGS);
		savings.withdraw(-4.0);
	}

}
