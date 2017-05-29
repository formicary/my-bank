package com.abc.account;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.account.AccountI;
import com.abc.account.Checking;


public class CheckingTest {
	private static final double DOUBLE_DELTA = 1e-15;
	@Test
	public void interestEarnedTest(){
		
		AccountI checking = new Checking();
		checking.deposit(100);
		double interest=checking.interestEarned();
		assertEquals(interest,100 * 0.001,DOUBLE_DELTA);
	}

}
