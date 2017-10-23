package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class CustomerTest {

	@Test
	/**
	 * Test the creation of a customer statement.
	 */
	public void testStatement() {

		Account checkingAccount = new Account(Account.AccountType.CHECKING);
		Account savingsAccount = new Account(Account.AccountType.SAVINGS);

		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Statement for Henry\n" +
						"\n" + "Checking Account\n" +
						"  Deposit $100.00\n" +
						"Total $100.00\n" +
						"\n" + "Savings Account\n" +
						"  Deposit $4,000.00\n" +
						"  Withdrawal $200.00\n" +
						"Total $3,800.00\n" +
						"\n" +
						"Total In All Accounts $3,900.00"
						, henry.getStatement());
	}

	@Test
	/**
	 * Test creating a customer with a single account.
	 */
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	/**
	 * Test creating a customer with two accounts.
	 */
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
		oscar.openAccount(new Account(Account.AccountType.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	/**
	 * Test creating a customer with three accounts.
	 */
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS))
				.openAccount(new Account(Account.AccountType.MAXI_SAVINGS))
				.openAccount(new Account(Account.AccountType.CHECKING));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	/**
	 * Test name is set properly on construction.
	 */
	public void name() {
		assertEquals("Bob", new Customer("Bob").getName());
	}

	@Test
	/**
	 * Test name can be changed after construction.
	 */
	public void nameChange() {
		Customer c = new Customer("Bob");
		c.setName("Steven");
		assertEquals("Steven", c.getName());
	}

	@Test
	/**
	 * Test accounts are being added when opened.
	 */
	public void accountsStored() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		Account a2 = new Account(Account.AccountType.CHECKING);
		oscar.openAccount(a1).openAccount(a2);
		assertSame(a1, oscar.getAccountListClone().get(0));
	}

	@Test
	/**
	 * Test account interator acts as expected.
	 */
	public void accountsIterator() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		Account a2 = new Account(Account.AccountType.CHECKING);
		Account a3 = new Account(Account.AccountType.CHECKING);
		Account a4 = new Account(Account.AccountType.CHECKING);
		Account a5 = new Account(Account.AccountType.CHECKING);
		oscar.openAccount(a1).openAccount(a2).openAccount(a3).openAccount(a4).openAccount(a5);

		Iterator<Account> i = oscar.getAccountIterator();
		i.next();
		i.next();
		i.next();
		i.next();
		assertSame(a5, i.next());
	}

	@Test
	/**
	 * Test can create a clone list of accounts.
	 */
	public void listClone() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1);
		assertSame(a1, oscar.getAccountListClone().get(0));
	}

	@Test
	/**
	 * Test that the clone list isn't connected to the original object.
	 */
	public void listCloneConncted() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1);

		List<Account> l = oscar.getAccountListClone();
		l.add(new Account(Account.AccountType.CHECKING));
		assertTrue(l.size() != oscar.getNumberOfAccounts());
	}

	@Test
	/**
	 * Test summing a customers total annual interest.
	 */
	public void totalInterstAnnual() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		a1.deposit(1000);
		a1.deposit(50);

		Account a2 = new Account(Account.AccountType.CHECKING);
		a2.deposit(2000);
		a2.deposit(70);

		oscar.openAccount(a1);
		oscar.openAccount(a2);

		assertTrue(oscar.getTotalAnnualInterestPayable().compareTo(new BigDecimal("3.17")) == 0);
	}

	@Test
	/**
	 * Test summing a customers total daily interest.
	 */
	public void totalInterstDaily() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		a1.deposit(1000);
		a1.deposit(50);

		Account a2 = new Account(Account.AccountType.CHECKING);
		a2.deposit(2000);
		a2.deposit(70);

		oscar.openAccount(a1);
		oscar.openAccount(a2);

		assertTrue(oscar.getTotalDailyInterestPayable().compareTo(new BigDecimal("0.008559")) == 0);
	}

	@Test
	/**
	 * Test the own account method when the customer does own the account.
	 */
	public void ownAccount() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1);
		assertEquals(oscar.checkOwnAccounts(a1), true);
	}

	@Test
	/**
	 * Test the own account method when the customer does own the accounts.
	 */
	public void ownAccounts() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		Account a2 = new Account(Account.AccountType.MAXI_SAVINGS);
		Account a3 = new Account(Account.AccountType.CHECKING);
		Account a4 = new Account(Account.AccountType.SAVINGS);
		Account a5 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1).openAccount(a2).openAccount(a3).openAccount(a4).openAccount(a5);
		assertEquals(oscar.checkOwnAccounts(a1, a2, a3, a4, a5), true);
	}

	@Test
	/**
	 * Test the own account method when the customer doesen't own the account.
	 */
	public void ownAccountFail() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		assertEquals(oscar.checkOwnAccounts(a1), false);
	}

	@Test
	/**
	 * Test the own account method when the customer doesen't own some accounts.
	 */
	public void ownAccountsFail() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		Account a2 = new Account(Account.AccountType.MAXI_SAVINGS);
		Account a3 = new Account(Account.AccountType.CHECKING);
		Account a4 = new Account(Account.AccountType.SAVINGS);
		Account a5 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1).openAccount(a4).openAccount(a5);
		assertEquals(oscar.checkOwnAccounts(a1, a2, a3, a4, a5), false);
	}

	@Test
	/**
	 * Test transferring all funds between accounts.
	 */
	public void transferAccount() {
		Customer oscar = new Customer("Oscar");
		Account from = new Account(Account.AccountType.SAVINGS);
		Account to = new Account(Account.AccountType.MAXI_SAVINGS);
		from.deposit(1000);
		oscar.openAccount(from).openAccount(to);
		oscar.accountTransfer(from, to, new BigDecimal("1000"));
		assertTrue(from.getTransactionsSum().compareTo(BigDecimal.ZERO) == 0
				&& to.getTransactionsSum().compareTo(new BigDecimal("1000")) == 0);
	}

	@Test
	/**
	 * Test transferring some funds between accounts.
	 */
	public void transferAccountPartial() {
		Customer oscar = new Customer("Oscar");
		Account from = new Account(Account.AccountType.SAVINGS);
		Account to = new Account(Account.AccountType.MAXI_SAVINGS);
		from.deposit(1000);
		to.deposit(10);
		oscar.openAccount(from).openAccount(to);
		oscar.accountTransfer(from, to, new BigDecimal("500"));
		assertTrue(from.getTransactionsSum().compareTo(new BigDecimal("500")) == 0
				&& to.getTransactionsSum().compareTo(new BigDecimal("510")) == 0);
	}

	@Test
	/**
	 * Test transferring funds when don't have required funds.
	 */
	public void transferAccountFailAmount() {
		Customer oscar = new Customer("Oscar");
		Account from = new Account(Account.AccountType.SAVINGS);
		Account to = new Account(Account.AccountType.MAXI_SAVINGS);
		from.deposit(999);
		oscar.openAccount(from).openAccount(to);
		assertTrue(oscar.accountTransfer(from, to, new BigDecimal("1000")) == false
				&& from.getTransactionsSum().compareTo(new BigDecimal("999")) == 0
				&& to.getTransactionsSum().compareTo(BigDecimal.ZERO) == 0);
	}

	@Test
	/**
	 * Test transferring funds when don't own from account.
	 */
	public void transferAccountFailOwnTo() {
		Customer oscar = new Customer("Oscar");
		Account from = new Account(Account.AccountType.SAVINGS);
		Account to = new Account(Account.AccountType.MAXI_SAVINGS);
		from.deposit(500);
		oscar.openAccount(to);
		assertTrue(oscar.accountTransfer(from, to, new BigDecimal("500")) == false
				&& from.getTransactionsSum().compareTo(new BigDecimal("500")) == 0
				&& to.getTransactionsSum().compareTo(BigDecimal.ZERO) == 0);
	}

	@Test
	/**
	 * Test transferring funds when don't own to account.
	 */
	public void transferAccountFailOwnFrom() {
		Customer oscar = new Customer("Oscar");
		Account from = new Account(Account.AccountType.SAVINGS);
		Account to = new Account(Account.AccountType.MAXI_SAVINGS);
		from.deposit(500);
		oscar.openAccount(from);
		assertTrue(oscar.accountTransfer(from, to, new BigDecimal("500")) == false
				&& from.getTransactionsSum().compareTo(new BigDecimal("500")) == 0
				&& to.getTransactionsSum().compareTo(BigDecimal.ZERO) == 0);
	}

	@Test
	/**
	 * Test calculating all accounts holdings.
	 */
	public void accountHoldings() {
		Customer oscar = new Customer("Oscar");
		Account a = new Account(Account.AccountType.CHECKING);
		a.deposit(1000);
		a.deposit(10);
		oscar.openAccount(a);
		a = new Account(Account.AccountType.CHECKING);
		a.deposit(75);
		oscar.openAccount(a);

		assertTrue(oscar.getTotalAccountHoldings().compareTo(new BigDecimal("1085")) == 0);
	}

	@Test
	/**
	 * Test toString method.
	 */
	public void stringCreation() {
		Customer oscar = new Customer("Oscar");
		Account a = new Account(Account.AccountType.CHECKING);
		a.deposit(1000);
		a.deposit(10);
		oscar.openAccount(a);
		a = new Account(Account.AccountType.CHECKING);
		a.deposit(75);
		oscar.openAccount(a);

		String expected = "Accounts: 2  Total Holdings: $1,085.00";
		assertEquals(expected, oscar.toString());
	}
}
