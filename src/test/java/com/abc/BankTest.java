package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BankTest {

	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void shouldPrintCorrectSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);
		assertEquals("Customer Summary\n - John (1 account)", bank.printCustomerSummary());
	}

	@Test
	public void shouldCalculateCorrectInterestForCheckingAccounts() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill");
		bill.openAccount(checkingAccount);
		bank.addCustomer(bill);
		checkingAccount.deposit(100.0);
		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void shouldCalculateCorrectInterestForSavingsAccounts() {
		Bank bank = new Bank();
		Account savingsAccount = new SavingsAccount();
		Customer bill = new Customer("Bill");
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);
		savingsAccount.deposit(1500.0);
		assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccountsRateShouldAccrue() {
		Bank bank = new Bank();
		Account savingsAccount = new SavingsAccount();
		Customer bill = new Customer("Bill");
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);
		savingsAccount.deposit(500.0);
		assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
		savingsAccount.addInterestToBalance(); // after 1 year
		savingsAccount.deposit(600.0);
		assertEquals(2.2, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void shouldCalculateCorrectInterestForMaxiSavingsAccounts() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount();
		Customer bill = new Customer("Bill");
		bill.openAccount(maxiSavingsAccount);
		bank.addCustomer(bill);
		maxiSavingsAccount.deposit(3000.0);
		assertEquals(315.47, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void MaxiSavingsAccountsRateShouldAccrue() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount();
		Customer bill = new Customer("Bill");
		bill.openAccount(maxiSavingsAccount);
		bank.addCustomer(bill);
		maxiSavingsAccount.deposit(1000.0);
		assertEquals(20.20, bank.totalInterestPaid(), DOUBLE_DELTA);
		maxiSavingsAccount.addInterestToBalance();// after 1 year
		maxiSavingsAccount.deposit(1000.0);
		assertEquals(212.44, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
}
