package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Customer bill;
	private Customer john;
	private Bank bank;
	private Account checkingAccount;
	private Account savingsAccount;
	private Account maxiSavingsAccount;

	@Before
	public void setUp() {
		bank = new Bank();
		john = new Customer("John");
		bill = new Customer("Bill");
		checkingAccount = new CheckingAccount();
		savingsAccount = new SavingsAccount();
		maxiSavingsAccount = new MaxiSavingsAccount();
	}

	/**
	 * Test generation of bank customer summary with one account
	 */
	@Test
	public void customerSummary() {      
		john.openAccount(checkingAccount);
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}
	/**
	 * Test generation of bank customer summary with more than one account
	 */
	@Test
	public void customerSummaryMultiple() {      
		john.openAccount(checkingAccount);
		john.openAccount(savingsAccount);
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
	}
	/**
	 * Test opening of checking account and total interest paid
	 */
	@Test
	public void checkingAccount() {
		bill.openAccount(checkingAccount);
		bank.addCustomer(bill);  
		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	/**
	 * Test opening of savings account with amount greater than $1000.00 and total interest paid
	 */
	@Test
	public void savingsAccountMaxInterest() {
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);
		savingsAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	/**
	 * Test opening of savings account with amount less than $1000.00 and total interest paid
	 */
	@Test
	public void savingsAccount() {
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);
		savingsAccount.deposit(500.0);

		assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	/**
	 * Test opening of maxi savings account and total interest paid
	 */
	@Test
	public void maxiSavingsAccount() {
		bill.openAccount(maxiSavingsAccount);
		bank.addCustomer(bill);
		maxiSavingsAccount.deposit(3000.0);

		assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	/**
	 * Test opening of multiple accounts and total interest paid
	 */
	@Test
	public void openMultipleAccounts() {
		bill.openAccount(checkingAccount);
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);  
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(1500.00);

		assertEquals(2.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}
	/**
	 * Test closing of accounts
	 */
	@Test
	public void closeMultipleAccounts() {
		bill.openAccount(checkingAccount);
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);  
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(1500.00);

		assertEquals("Customer Summary\n - Bill (2 accounts)", bank.customerSummary());
		
		//Close Bill's accounts and remove details from bank
		bill.closeAccount(checkingAccount);
		bill.closeAccount(savingsAccount);
		bank.removeCustomer(bill);

		assertEquals("Customer Summary", bank.customerSummary());
	}

}
