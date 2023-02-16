package com.abc;

import org.junit.Test;
import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {

	@Test
	public void testInterestEarned() {
		Customer customer = new Customer("John Doe");
		AccountType accountType = AccountType.MAXI_SAVINGS;
		MaxiSavingsAccount account = new MaxiSavingsAccount(customer, accountType);

		account.deposit(1000);
		assertEquals(20.0, account.interestEarned(), 0.0);

		account.deposit(500);
		assertEquals(45.0, account.interestEarned(), 0.0);

		account.deposit(1000);
		assertEquals(120.0, account.interestEarned(), 0.0);
	}

}
