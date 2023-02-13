package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	@Test
	public void testOpenAccount() {
		Customer customer = new Customer("John Doe");
		Bank bank = new Bank();
		customer.setBank(bank);
		Account account = customer.openAccount(Account.AccountType.SAVINGS);
		assertEquals(1, customer.getNumberOfAccounts());
		assertEquals(customer, account.getCustomer());
	}

	@Test
	public void testTotalInterestEarned() {
		Customer customer = new Customer("John Doe");
		Bank bank = new Bank();
		customer.setBank(bank);
		Account account = customer.openAccount(Account.AccountType.SAVINGS);
		account.deposit(100.0);
		assertEquals(0.1, customer.totalInterestEarned(), 0.001);
	}

	@Test
	public void testOneAccount() {
		Customer customer = new Customer("Oscar");
		Bank bank = new Bank();
		customer.setBank(bank);
		Account account = customer.openAccount(Account.AccountType.CHECKING);
		assertEquals(1, customer.getNumberOfAccounts());
		assertEquals(customer, account.getCustomer());
	}

	@Test
	public void testTwoAccount() {
		Customer customer = new Customer("Oscar");
		Bank bank = new Bank();
		customer.setBank(bank);
		Account account1 = customer.openAccount(Account.AccountType.CHECKING);
		Account account2 = customer.openAccount(Account.AccountType.SAVINGS);
		assertEquals(2, customer.getNumberOfAccounts());
		assertEquals(customer, account1.getCustomer());
		assertEquals(customer, account2.getCustomer());
	}

	@Test
	public void testThreeAcounts() {
		Customer customer = new Customer("Oscar");
		Bank bank = new Bank();
		customer.setBank(bank);
		Account account1 = customer.openAccount(Account.AccountType.CHECKING);
		Account account2 = customer.openAccount(Account.AccountType.SAVINGS);
		Account account3 = customer.openAccount(Account.AccountType.MAXI_SAVINGS);
		assertEquals(3, customer.getNumberOfAccounts());
		assertEquals(customer, account1.getCustomer());
		assertEquals(customer, account2.getCustomer());
		assertEquals(customer, account3.getCustomer());
	}

	@Test
	public void testGetStatement() {
		Customer customer = new Customer("John Doe");
		Bank bank = new Bank();
		customer.setBank(bank);
		Account account = customer.openAccount(Account.AccountType.SAVINGS);
		account.deposit(100.0);
		account.withdraw(50.0);
		assertEquals("Savings Account\n  deposit $100.00\n  withdrawal $50.00\nTotal $50.00",
				customer.statementForAccount(account));
	}

}
