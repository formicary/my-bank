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


	@Test
	public void customerSummary() {      
		john.openAccount(checkingAccount);
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		bill.openAccount(checkingAccount);
		bank.addCustomer(bill);  
		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccount() {
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);
		savingsAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccount() {
		bill.openAccount(maxiSavingsAccount);
		bank.addCustomer(bill);
		maxiSavingsAccount.deposit(3000.0);

		assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

}
