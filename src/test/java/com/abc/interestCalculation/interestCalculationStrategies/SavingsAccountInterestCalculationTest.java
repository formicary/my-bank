package com.abc.interestCalculation.interestCalculationStrategies;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mockito.Mockito;

import com.abc.Account;
import com.abc.AccountType;
import com.abc.interestCalculation.InterestCalculator;

public class SavingsAccountInterestCalculationTest {
	
	private static final double DOUBLE_DELTA = 1e-10;
	
	@Test
	public void calculateInterestUnder1000() {
		
		InterestCalculator interestCalculator = new InterestCalculator();
		
		Account account = Mockito.mock(Account.class);
		
		Mockito.when(account.getAccountType()).thenReturn(AccountType.SAVINGS);
		Mockito.when(account.getCurrentBalance()).thenReturn(999.0);
		Mockito.when(account.getDaysSinceLastTransaction()).thenReturn((long) 3);

		System.out.println(interestCalculator.calculateInterest(account));
		assertEquals(0.008206889869825318, interestCalculator.calculateInterest(account), DOUBLE_DELTA);

	}
	
	@Test
	public void calculateInterestOver1000() {
		
		InterestCalculator interestCalculator = new InterestCalculator();
		
		Account account = Mockito.mock(Account.class);
		
		Mockito.when(account.getAccountType()).thenReturn(AccountType.SAVINGS);
		Mockito.when(account.getCurrentBalance()).thenReturn(1000.0);
		Mockito.when(account.getDaysSinceLastTransaction()).thenReturn((long) 3);

		System.out.println(interestCalculator.calculateInterest(account));
		assertEquals(0.008215127448352177, interestCalculator.calculateInterest(account), DOUBLE_DELTA);

	}
}
