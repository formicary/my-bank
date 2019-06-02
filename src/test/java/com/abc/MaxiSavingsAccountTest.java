package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {
	
	 private static final double DOUBLE_DELTA = 1e-15;
	 
	 private Account account = new MaxiSavingsAccount();

	 @Test
	 public void testName() {
		 assertEquals("Maxi Savings Account", account.getAccountType());	 
	 }
	 
	 @Test
	 public void interestEmpty() {
		 assertEquals(0.0, account.interestEarned(), DOUBLE_DELTA);
	 }
	 
	 @Test
	 public void interest(){
		 account.deposit(1500.0);
		 assertEquals(75.0, account.interestEarned(), DOUBLE_DELTA);
	 }
	 
	 @Test
	 public void interestWithdraw(){
		 account.deposit(2000.0);
		 account.withdraw(500.0);
		 assertEquals(1.5, account.interestEarned(), DOUBLE_DELTA);
	 }

}
