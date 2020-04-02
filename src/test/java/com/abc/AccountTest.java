package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

public class AccountTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void CheckingAccountWithdrawNegativAmount () {
		Account a = new CheckingAccount();
		a.withdraw(-100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CheckingAccountWithdraw() {
		Account a = new CheckingAccount();
		a.withdraw(100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void SavingsAccountWithdrawNegativAmount () {
		Account a = new SavingsAccount();
		a.withdraw(-100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void SavingsAccountWithdraw() {
		Account a = new SavingsAccount();
		a.withdraw(100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void MaxiSavingsAccountWithdrawNegativAmount () {
		Account a = new MaxiSavingsAccount();
		a.withdraw(-100);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void MaxiSavingsAccountWithdraw() {
		Account a = new MaxiSavingsAccount();
		a.withdraw(100);
	}
}
