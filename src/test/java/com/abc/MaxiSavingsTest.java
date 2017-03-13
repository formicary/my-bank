package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.junit.Test;

public class MaxiSavingsTest {

	private static final double DOUBLE_DELTA = 1e-15;
	private MaxiSavingsAccount ms = new MaxiSavingsAccount();
	Bank bank = new Bank();
	Customer moneyBags = new Customer("Money Bags");
	
	/**
	 * Test to check compliance with Maxi Savings Interest
	 * No withdrawals within past 10 days --> interest rate of 5%
	 */	
	@Test
	public void testMaxiSavingsCompliant() {
		
		ms.deposit(1000.00);
		ms.withdraw(20.00);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -11);
		Date date = cal.getTime();
		for(Map.Entry<Transaction, Integer> entry: ms.getTransactions().entrySet()) {
			entry.getKey().setTransactionDate(date);
		}
		moneyBags.openAccount(ms);
		bank.addCustomer(moneyBags);
		assertEquals(49.0, moneyBags.totalInterestEarned(), DOUBLE_DELTA);
	}
	/**
	 * Test to check compliance with Maxi Savings Interest
	 * No withdrawals within past 10 days --> interest rate of 5%
	 */	
	@Test
	public void testMaxiSavingsBoundary() {
		
		ms.deposit(1000.00);
		ms.withdraw(20.00);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		Date date = cal.getTime();
		for(Map.Entry<Transaction, Integer> entry: ms.getTransactions().entrySet()) {
			entry.getKey().setTransactionDate(date);
		}
		moneyBags.openAccount(ms);
		bank.addCustomer(moneyBags);
		assertEquals(0.98, moneyBags.totalInterestEarned(), DOUBLE_DELTA);
	}	
	/**
	 * Test to check non-compliance with Maxi Savings Interest
	 * Withdrawal within past 10 days --> interest rate of 0.1%
	 */
	@Test
	public void testMaxiSavingsNonCompliant() {
		
		ms.deposit(1000.00);
		ms.withdraw(20.00);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		Date date = cal.getTime();
		for(Map.Entry<Transaction, Integer> entry: ms.getTransactions().entrySet()) {
			entry.getKey().setTransactionDate(date);
		}
		moneyBags.openAccount(ms);
		bank.addCustomer(moneyBags);
		assertEquals(0.98, moneyBags.totalInterestEarned(), DOUBLE_DELTA);		
	}
	/**
	 * Test to check compliance with Maxi Savings Interest
	 * Deposit within past 10 days --> interest rate of 5%
	 */
	@Test
	public void testMaxiSavingsDeposit() {
		
		ms.deposit(1000.00);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		Date date = cal.getTime();
		for(Map.Entry<Transaction, Integer> entry: ms.getTransactions().entrySet()) {
			entry.getKey().setTransactionDate(date);
		}
		moneyBags.openAccount(ms);
		bank.addCustomer(moneyBags);
		assertEquals(50.00, moneyBags.totalInterestEarned(), DOUBLE_DELTA);		
	}

}
