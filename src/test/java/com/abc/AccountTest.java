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
	 * 
	 */
	@Test
	public void testInvalidDeposit() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> testAccount1.deposit(-500));
	}
	
	/**
	 * 
	 */
	@Test
	public void testInvalidWithdrawal() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> testAccount1.withdraw(-500));
	}

	/**
	 * 
	 */
	@Test
	public void testEmptyAccountsInterest() {
		assertEquals(0, testAccount1.interestEarned());
	}
	
	/**
	 * 
	 */
	@Test 
	public void testSmallSavings() {
		testAccount2.deposit(999);
		assertEquals((999 * 0.001), testAccount2.interestEarned());
	}
}
