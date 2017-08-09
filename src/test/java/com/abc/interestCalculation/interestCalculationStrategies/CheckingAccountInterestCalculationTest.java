package com.abc.interestCalculation.interestCalculationStrategies;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import com.abc.Account;
import com.abc.AccountType;
import com.abc.interestCalculation.InterestCalculator;



public class CheckingAccountInterestCalculationTest {
	
	private static final double DOUBLE_DELTA = 1e-10;
	
	@Test
	public void calculateInterest() {
		
		InterestCalculator interestCalculator = new InterestCalculator();
		
		Account account = Mockito.mock(Account.class);
		
		Mockito.when(account.getAccountType()).thenReturn(AccountType.CHECKING);
		Mockito.when(account.getCurrentBalance()).thenReturn(100.0);
		Mockito.when(account.getDaysSinceLastTransaction()).thenReturn((long) 365);

		assertEquals(0.1, interestCalculator.calculateInterest(account), DOUBLE_DELTA);

	}
}
