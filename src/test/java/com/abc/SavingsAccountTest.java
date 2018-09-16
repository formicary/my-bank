package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SavingsAccountTest {
	
	private static final String ACCOUNT_NUMBER = "ABC";

	@Test
	public void interestEarnedOnLessThan1000ReturnsInterest()
	{
		//Given
		double expected = 0.027;
		
		//When
		SavingsAccount savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER);
		savingsAccount.deposit(100.00);
		double actual = savingsAccount.interestEarned();
		
		//Then
		assertEquals(expected, actual, 0.001d);
	}
	
	@Test
	public void interestEarnedOnGreaterThan1000ReturnsInterest()
	{
		//Given
		double expected = 5.927d;
		
		//When
		SavingsAccount savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER);
		savingsAccount.deposit(10000.00);
		double actual = savingsAccount.interestEarned();
		
		//Then
		assertEquals(expected, actual, 0.001d);
	}
}
