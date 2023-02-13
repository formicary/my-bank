package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Account.AccountType;

public class CheckingAccountTest {

	private final String TEST_CUSTOMER_NAME = "Rani";
	private final double DELTA = 1e-15;

	@Test
	public void testInterestEarned() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.CHECKING);
		account.deposit(1900.0);
		assertEquals(1.9, account.interestEarned(), DELTA);
	}

}
