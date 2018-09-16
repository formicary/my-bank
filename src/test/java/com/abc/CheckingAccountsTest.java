package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CheckingAccountsTest {
	@Test
	public void interestEarnedReturnsInterest()
	{
		//Given
		double expected = 0.1;
		
		//When
		CheckingAccount checkingAccount = AccountsTestHelper.createCheckingAcount("ABC");
		checkingAccount.deposit(100.00);
		double actual = checkingAccount.interestEarned();
		
		//Then
		assertEquals(expected, actual, 0.0d);
	}
}
