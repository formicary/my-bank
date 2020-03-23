package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void AccountWithdrawNegativAmount () {
		Account a = new Account(0);
		a.withdraw(-100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void AccountWithdraw() {
		Account a = new Account(0);
		a.withdraw(100);
	}

}
