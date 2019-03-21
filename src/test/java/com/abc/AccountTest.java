package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

	@Test //Test for account type testing
	public void getAccountTypeCheckingTest() {
		Account account = new Account(Account.CHECKING);
		assertEquals(0, account.getAccountType());	
	}
	@Test
	public void getAccountTypeSavingsTest() {
		Account account = new Account(Account.SAVINGS);
		assertEquals(1, account.getAccountType());	
	}
	
	@Test
	public void getAccountTypeMaxiSavingsTest() {
		Account account = new Account(Account.MAXI_SAVINGS);
		assertEquals(2, account.getAccountType());
	}

}
