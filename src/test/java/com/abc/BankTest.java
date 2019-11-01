package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void testCustomerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	// added case for 2 accounts
	@Test
	public void testCustomerSummary2() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
		john.openAccount(new Account(Account.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
	}

	// added case for 3 accounts
	@Test
	public void testCustomerSummary3() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
		john.openAccount(new Account(Account.SAVINGS));
		john.openAccount(new Account(Account.MAXI_SAVINGS));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (3 accounts)", bank.customerSummary());
	}

	@Test
	public void testCheckingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(2600.0);
		// calls the test function that is the same but sets the date as a year ahead
		assertEquals(2.6, bank.totalInterestPaidTest(), DOUBLE_DELTA);
	}

	@Test
	public void testSavingsAccount() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

		savingsAccount.deposit(1500.0);
		// calls the test function that is the same but sets the date as a year ahead
		assertEquals(2.0, bank.totalInterestPaidTest(), DOUBLE_DELTA);
	}

	@Test
	public void testSavingsAccountLess1000() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

		savingsAccount.deposit(500.0);
		// calls the test function that is the same but sets the date as a year ahead
		assertEquals(0.5, bank.totalInterestPaidTest(), DOUBLE_DELTA);
	}

	@Test
	public void testMaxiSavingsAccountNoWithdrawal() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(200.0);
		// calls the test function that is the same but sets the date as a year ahead
		assertEquals(10.45, bank.totalInterestPaidTest(), DOUBLE_DELTA);
	}

	// tests the Maxi saving account but with a withdrawal
	@Test
	public void testMaxiSavingsAccountWithdrawal() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(1100.0);
		maxiSavingsAccount.withdraw(500.0);
		// calls the test function that is the same but sets the date as a year ahead
		assertEquals(29.88, bank.totalInterestPaidTest(), DOUBLE_DELTA);
	}

	// does the same for individual accounts but for multiple different types for
	// one person
	@Test
	public void tesMultipleAccounts() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Account checkingAccount2 = new Account(Account.CHECKING);
		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		Account savingsAccount = new Account(Account.SAVINGS);
		Customer bill = new Customer("Bill");
		bill.openAccount(checkingAccount);
		bill.openAccount(checkingAccount2);
		bill.openAccount(maxiSavingsAccount);
		bill.openAccount(savingsAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(1000.0);
		checkingAccount2.deposit(2100.0);
		maxiSavingsAccount.deposit(1100.0);
		maxiSavingsAccount.withdraw(500.0);
		savingsAccount.deposit(1300.0);
		// calls the test function that is the same but sets the date as a year ahead
		assertEquals(34.58, bank.totalInterestPaidTest(), DOUBLE_DELTA);
	}

	// tests the fix for get first customer
	@Test
	public void testFirstCustomer() throws IOException {
		Bank bank = new Bank();
		Customer oscar = new Customer("Oscar");
		Customer jim = new Customer("Jim");
		bank.addCustomer(oscar);
		bank.addCustomer(jim);
		assertEquals("Oscar", bank.getFirstCustomer());

	}

}
