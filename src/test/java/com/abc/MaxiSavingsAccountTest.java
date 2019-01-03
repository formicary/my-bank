package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class MaxiSavingsAccountTest {

	Customer john = new Customer("john");
	MaxiSavingsAccount maxiAccount = new MaxiSavingsAccount();
	private static final double DOUBLE_DELTA = 1e-15;
	DateProvider dateProvider = new DateProvider();
	
//	@Test
//	public void interestEarnedMaxiAccount1() {
//		maxiAccount.deposit(50);
//		assertEquals(1,maxiAccount.interestEarned(), DOUBLE_DELTA);
//	}
//	
//	@Test 
//	public void interestEarnedMaxiAccount2() {
//		maxiAccount.deposit(2000);
//		assertEquals(70, maxiAccount.interestEarned(),  DOUBLE_DELTA);
//	}
//	
//	@Test 
//	public void interestEarnedMaxiAccount3() {
//		maxiAccount.deposit(3000);
//		assertEquals(170, maxiAccount.interestEarned(), DOUBLE_DELTA);
//	}
	
	@Test
	public void interestEarned1() {
		john.openAccount(maxiAccount);
		maxiAccount.deposit(50);
	assertEquals(2.5, maxiAccount.interestEarned(), DOUBLE_DELTA);
	}
	
	@Test
	public void interestEarned2() { 
		
		Date tenDaysPrior = dateProvider.tenDaysBeforeCurrentDate();

		john.openAccount(maxiAccount);
		maxiAccount.deposit(50);
		maxiAccount.deposit(500);
		
		maxiAccount.transactions.get(1).setTransactionDate(tenDaysPrior);

		assertEquals(0.55, maxiAccount.interestEarned(), DOUBLE_DELTA);
	}
	
}
