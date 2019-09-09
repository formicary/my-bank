package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AccountTest {

	@Test
	public void withdrawingAmountMoreThanBalance() {
		Account a = new CheckingAccount();
		a.deposit(500.0);
		try {
			a.withdraw(600.0);
		} catch (Exception e) {
			String expectedMessage = "withdrawal amount must be less than or equal to balance";
			assertEquals("Exception message must be correct", expectedMessage, e.getMessage());
		}
	}

	@Test
	public void withdrawingAmountOfZero() {
		Account a = new CheckingAccount();
		a.deposit(500.0);
		try {
			a.withdraw(0.0);
		} catch (Exception e) {
			String expectedMessage = "amount must be greater than zero";
			assertEquals("Exception message must be correct", expectedMessage, e.getMessage());
		}
	}

	@Test
	public void depositingAmountOfZero() {
		Account a = new CheckingAccount();
		try {
			a.deposit(0.0);
		} catch (Exception e) {
			String expectedMessage = "amount must be greater than zero";
			assertEquals("Exception message must be correct", expectedMessage, e.getMessage());
		}
	}
}
