package com.abc.calculators;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;
import com.abc.model.Money;
import com.abc.providers.DateProvider;


public class MaxiSavingAccountInterestCalculatorTest {
	

	private final static String DATE_BEFORE_THRESHOLD = "09-08-2015";
	private final static String DATE_AFTER_THRESHOLD = "01-07-2015";
	
	@Mock
	private DateProvider dateProvider;
	
	@InjectMocks
	private final IntrestCalculator interestCalculator = new MaxiSavingAccountInterestCalculator();	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		// today date 10-08-2015
		Mockito.when(dateProvider.getDate()).thenReturn(new GregorianCalendar(2015, 7, 10));
	}
	
	

	@Test
	public void shouldCalculateIntrest(){
		Account account = AccountBuilder.createMaxiSaving()
						.withDeposit("1000", DATE_AFTER_THRESHOLD)
						.withInterest("10", DATE_BEFORE_THRESHOLD)
						.withDeposit("90", DATE_BEFORE_THRESHOLD).get();
		
		Money calculatedIntrest = interestCalculator.calculateInterestEarned(account);
		
		assertEquals(new Money("6.00"), calculatedIntrest);
	}
	
	
	
	
	@Test
	public void shouldExcludeWithdrawalBeforeThreshold(){
		Account account = AccountBuilder.createMaxiSaving()
						.withDeposit("1000", DATE_AFTER_THRESHOLD)
						.withWithdrawal("10", DATE_BEFORE_THRESHOLD)
						.get();
		
		Money calculatedIntrest = interestCalculator.calculateInterestEarned(account);
		
		assertEquals(new Money("1.00"), calculatedIntrest);
	}
	
	
	
	
	@Test
	public void shouldIncludeWithdrawalAfterThreshold(){
		Account account = AccountBuilder.createMaxiSaving()
						.withDeposit("1000", DATE_AFTER_THRESHOLD)
						.withWithdrawal("10", DATE_AFTER_THRESHOLD)
						.get();
		
		Money calculatedIntrest = interestCalculator.calculateInterestEarned(account);
		
		assertEquals(new Money("0.99"), calculatedIntrest);
	}
	
}
