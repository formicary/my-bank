package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test 
	public void testDeposit() {
		// normal
		Account a = new Account(AccountType.SAVINGS);
		a.deposit(100);
		assertEquals(100, a.sumTransactions(), DOUBLE_DELTA);
		
		// negative value
		assertEquals(false, a.deposit(-2));
	}
	
	@Test 
	public void testWithdraw() {
		Account a = new Account(AccountType.CHECKING);
		a.deposit(100);
		
		// withdraw > amount in the account
		assertEquals(false, a.withdraw(101));
		
		// negative value
		assertEquals(false, a.withdraw(-1.2));

	}
	
	@Test 
	public void testGetAccountType() {
		Account a = new Account(AccountType.MAXI_SAVINGS);
		assertEquals(AccountType.MAXI_SAVINGS, a.getAccountType());
	}

}
