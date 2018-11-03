package com.abc;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    
	
	@Test
	public void deposit_positiveAmount_correctValue() {
		Account a = new Account(AccountType.CHECKING);
		a.deposit(10000);
		
		assertEquals(10000, a.sumTransactions(), DOUBLE_DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deposit_negativeAmount_throwException() {
		Account a = new Account(AccountType.CHECKING);
		a.deposit(-10000);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void withdraw_zeroBalance_throwException() {
		Account a = new Account(AccountType.CHECKING);
		a.withdraw(5000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void withdraw_largerThanBalance_throwException() {
		Account a = new Account(AccountType.CHECKING);
		a.deposit(3000);
		a.withdraw(5000);
	}
	
	
	
	@Test
	public void withdraw_positiveAmount_correctValue() {
		Account a = new Account(AccountType.CHECKING);
		a.deposit(10000);
		a.withdraw(5000);
		
		assertEquals(5000, a.sumTransactions(), DOUBLE_DELTA);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void withdraw_negativeAmount_throwException() {
		Account a = new Account(AccountType.CHECKING);
		a.deposit(10000);
		a.withdraw(-5000);
	}

}
