package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

	private static final double DOUBLE_DELTA = 1e-15;
	Bank bank = new Bank();
	Customer john = new Customer("John");
	CheckingAccount checkingAccount = new CheckingAccount();
	MaxiSavingsAccount maxiAccount = new MaxiSavingsAccount();
	SavingsAccount savingsAccount = new SavingsAccount();

	@Test
	public void customerSummary() {
		
		john.openAccount(checkingAccount);
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void interestPaidAcross3Accounts() {
		
		john.openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiAccount);
		bank.addCustomer(john);
		checkingAccount.deposit(100.0);
		maxiAccount.deposit(50.0);
		savingsAccount.deposit(1500.0);

		assertEquals(4.6, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void getFirstCustomerTest() {
		
		Customer oscar = new Customer("Oscar");
		Customer mary = new Customer("Mary");
		bank.addCustomer(john);
		bank.addCustomer(oscar);
		bank.addCustomer(mary);

		assertEquals("John", bank.getFirstCustomer());
	}
}
