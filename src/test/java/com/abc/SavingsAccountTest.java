package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SavingsAccountTest {
	
	private Savings_Account acc;

	@Before
	public void setUp() throws Exception {
		acc = new Savings_Account(TestConstants.SAVINGS_ACCOUNT, TestConstants.FIRST_ACCOUNT_ID);
	}

	@Test
	public void testInterestEarned() {
		assertEquals(TestConstants.ZERO_BD, acc.interestEarned());
		
		acc.deposit(TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.SAVINGS_ACCOUNT_INTEREST_EARNED_FIRST_TIER, acc.interestEarned());
		
		acc.deposit(TestConstants.SECOND_TIER_DEPOSIT);
		assertEquals(TestConstants.SAVINGS_ACCOUNT_INTEREST_EARNED_SECOND_TIER, acc.interestEarned());
	}
	
	@After
	public void tearDown() throws Exception {
		acc = null;
	}

}
