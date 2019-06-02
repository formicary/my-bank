package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {
	
	 private static final double DOUBLE_DELTA = 1e-15;
	 
	 private Account account = new SavingsAccount();
	 
	 @Test
	 public void testName() {
		 assertEquals("Savings Account", account.getAccountType());	 
	 }
	 
	 @Test
	 public void interestEmpty() {
		 assertEquals(0.0, account.interestEarned(), DOUBLE_DELTA);
	 }
	 
	 @Test
	 public void interest() {
		 account.deposit(1500.0);
		 assertEquals(2.0, account.interestEarned(), DOUBLE_DELTA);
	 }

}
