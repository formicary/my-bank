package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MaxiSavingsAccountTest {

	private MaxiSavings_Account acc;

	@Before
	public void setUp() throws Exception {
		acc = new MaxiSavings_Account(TestConstants.MAXISAVINGS_ACCOUNT, TestConstants.FIRST_ACCOUNT_ID);
	}

	@Test
	public void testInterestEarned() {
		assertEquals(TestConstants.ZERO_BD, acc.interestEarned());
		
		acc.deposit(TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.MAXISAVINGS_INTEREST_EARNED_FIRST_TIER, acc.interestEarned());
		
		acc.deposit(TestConstants.SECOND_TIER_DEPOSIT);
		assertEquals(TestConstants.MAXISAVINGS_INTEREST_EARNED_SECOND_TIER, acc.interestEarned());
		
		acc.deposit(TestConstants.THIRD_TIER_DEPOSIT);
		assertEquals(TestConstants.MAXISAVINGS_INTEREST_EARNED_THIRD_TIER, acc.interestEarned());
	}
	
	@After
	public void tearDown() throws Exception {
		acc = null;
	}

}
