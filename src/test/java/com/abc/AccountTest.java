package com.abc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {

	Account testAccount1, testAccount2;
	
	@BeforeEach
	void setUp() throws Exception {
		testAccount1 = new Account(AccountType.CHECKING);
		testAccount2 = new Account(AccountType.SAVINGS);
	}

	/**
	 * deposit below 0 should throw an exception
	 */
	@Test
	public void testInvalidDeposit() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> testAccount1.deposit(-500));
	}
	
	/**
	 * withdrawal below 0 should throw an exception
	 */
	@Test
	public void testInvalidWithdrawal() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> testAccount1.withdraw(-500));
	}

	/**
	 * accounts with no deposits made should not accrue interest
	 */
	@Test
	public void testEmptyAccountsInterest() {
		assertEquals(0, testAccount1.interestEarned());
	}
	
	/**
	 * savings accounts with total of less than 1000 should have interest rate 0.1%
	 */
	@Test 
	public void testSmallSavings() {
		testAccount2.deposit(999);
		assertEquals((999 * 0.001), testAccount2.interestEarned());
	}
}
