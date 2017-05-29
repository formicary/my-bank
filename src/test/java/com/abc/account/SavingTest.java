package com.abc.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.account.AccountI;
import com.abc.account.Saving;

public class SavingTest {
	private static final double DOUBLE_DELTA = 1e-15;
	@Test
	public void interestEarnedTest1(){
		
		AccountI saving= new Saving();
		saving.deposit(1000);
		double interest=saving.interestEarned();
		assertEquals(interest,900 * 0.001,DOUBLE_DELTA);
		
		
		
		
	}

    


}
