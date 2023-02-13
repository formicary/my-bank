package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Account.AccountType;

public class SavingsAccountTest {

	private final String TEST_CUSTOMER_NAME = "Rani";
	private final double DELTA = 1e-15;

	@Test
	public void testInterestEarnedWithAmountLessThan1000() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(900.0);
		assertEquals(0.9, account.interestEarned(), DELTA);
	}

	@Test
	public void testInterestEarnedWithAmountGreaterThan1000() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(1900.0);
		assertEquals(2.8, account.interestEarned(), DELTA);
	}
}
