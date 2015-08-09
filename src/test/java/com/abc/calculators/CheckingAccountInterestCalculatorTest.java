package com.abc.calculators;

import static org.junit.Assert.assertEquals;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.abc.model.Account;
import com.abc.model.AccountBuilder;
import com.abc.model.Money;

@RunWith(JUnitParamsRunner.class)
public class CheckingAccountInterestCalculatorTest {


	private final IntrestCalculator interestCalculator = new CheckingAccountInterestCalculator();	
	
	@Test
	@Parameters({"0.00, 0.00", 
				"0.00, 0.01",
				"0.00, 1",
				"0.01, 10",
				"0.10, 100",
				"1.00, 1000",
				"1.50, 1500",
				"2.00, 2000"})
	public void shouldCalculateIntrest(String expectedIntrest, String balance){
		Account account = AccountBuilder.createSaving().withDeposit(balance).get();
		
		Money calculatedIntrest = interestCalculator.calculateInterestEarned(account);
		
		assertEquals(new Money(expectedIntrest), calculatedIntrest);
	}
}
