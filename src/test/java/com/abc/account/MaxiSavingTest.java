package com.abc.account;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.account.AccountI;
import com.abc.account.MaxiSaving;


public class MaxiSavingTest {
	
	//test interest when withdrawals are in the transactions in the past 10 days
	private static final double DOUBLE_DELTA = 1e-15;
	@Test
	public void InterestEarnedTest1(){
		
		AccountI maxiSaving = new MaxiSaving();
		maxiSaving.deposit(2000);
		maxiSaving.withdraw(1000);
		double interest=maxiSaving.interestEarned();
		assertEquals(1000 * 0.001,interest,DOUBLE_DELTA);
		
	}
	//test interest when no withdrawals in the past 10 days
	@Test
	public void InterestEarnedTest2(){
		
		AccountI maxiSaving = new MaxiSaving();
		maxiSaving.deposit(1000);
		
		double interest=maxiSaving.interestEarned();
		assertEquals(1000 * 0.05,interest,DOUBLE_DELTA);
		
	}
	

}
