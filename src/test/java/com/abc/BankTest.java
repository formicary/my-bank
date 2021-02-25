package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);
		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0, "CHECKING");

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0, "SAVINGS");

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000.0, "MAXISAVINGS");

		assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void accountBalance() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		bank.addCustomer(new Customer("Joe").openAccount(checkingAccount));

		checkingAccount.deposit(1000.0, "CHECKING");

		assertEquals(1000.0, checkingAccount.getBalance(), DOUBLE_DELTA);
	}

	@Test
	public void accountIsExist() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer joe = new Customer("Joe").openAccount(checkingAccount);
		bank.addCustomer(joe);

		checkingAccount.deposit(100.0, "CHECKING");

		assertTrue(joe.accountTypeIsExist(checkingAccount));
	}
	
	@Test
	public void accountNotExist() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();
		Customer joe = new Customer("Joe").openAccount(checkingAccount);
		bank.addCustomer(joe);
		
		checkingAccount.deposit(100.0, "CHECKING");
		
		assertFalse(joe.accountTypeIsExist(savingsAccount));
	}

	@Test
	public void getFirstCustomer() {
		Bank bank = new Bank();
		assertEquals("There is no any customer", bank.getFirstCustomer());
		bank.addCustomer(new Customer("john"));
		bank.addCustomer(new Customer("bill"));
		assertEquals("john", bank.getFirstCustomer());
	}

}
