package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SavingsAccountTest {
	
	private static final String ACCOUNT_NUMBER = "ABC";

	@Test
	public void interestEarnedOnLessThan1000ReturnsInterest()
	{
		//Given
		double expected = 0.1;
		
		//When
		SavingsAccount savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER);
		savingsAccount.deposit(100.00);
		double actual = savingsAccount.interestEarned();
		
		//Then
		assertEquals(expected, actual, 0.0d);
	}
	
	@Test
	public void interestEarnedOnGreaterThan1000ReturnsInterest()
	{
		//Given
		double expected = 19.0d;
		
		//When
		SavingsAccount savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER);
		savingsAccount.deposit(10000.00);
		double actual = savingsAccount.interestEarned();
		
		//Then
		assertEquals(expected, actual, 0.0d);
	}
}
