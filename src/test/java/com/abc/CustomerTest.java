package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/**
 * Test cases for testing the customer class
 * 
 * @author CJSm
 *
 */
public class CustomerTest {

	/**
	 * Test the creation of a customer statement.
	 */
	@Test
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

	/**
	 * Test creating a customer with a single account.
	 */
	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	/**
	 * Test creating a customer with two accounts.
	 */
	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
		oscar.openAccount(new Account(Account.AccountType.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	/**
	 * Test creating a customer with three accounts.
	 */
	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS))
				.openAccount(new Account(Account.AccountType.MAXI_SAVINGS))
				.openAccount(new Account(Account.AccountType.CHECKING));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	/**
	 * Test name is set properly on construction.
	 */
	@Test
	public void name() {
		assertEquals("Bob", new Customer("Bob").getName());
	}

	/**
	 * Test name can be changed after construction.
	 */
	@Test
	public void nameChange() {
		Customer c = new Customer("Bob");
		c.setName("Steven");
		assertEquals("Steven", c.getName());
	}

	/**
	 * Test accounts are being added when opened.
	 */
	@Test
	public void accountsStored() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		Account a2 = new Account(Account.AccountType.CHECKING);
		oscar.openAccount(a1).openAccount(a2);
		assertSame(a1, oscar.getAccountListClone().get(0));
	}

	/**
	 * Test account interator acts as expected.
	 */
	@Test
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

	/**
	 * Test can create a clone list of accounts.
	 */
	@Test
	public void listClone() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1);
		assertSame(a1, oscar.getAccountListClone().get(0));
	}

	/**
	 * Test that the clone list isn't connected to the original object.
	 */
	@Test
	public void listCloneConncted() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1);

		List<Account> l = oscar.getAccountListClone();
		l.add(new Account(Account.AccountType.CHECKING));
		assertTrue(l.size() != oscar.getNumberOfAccounts());
	}

	/**
	 * Test summing a customers total annual interest.
	 */
	@Test
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

	/**
	 * Test summing a customers total daily interest.
	 */
	@Test
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

	/**
	 * Test the own account method when the customer does own the account.
	 */
	@Test
	public void ownAccount() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		oscar.openAccount(a1);
		assertEquals(oscar.checkOwnAccounts(a1), true);
	}

	/**
	 * Test the own account method when the customer does own the accounts.
	 */
	@Test
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

	/**
	 * Test the own account method when the customer doesen't own the account.
	 */
	@Test
	public void ownAccountFail() {
		Customer oscar = new Customer("Oscar");
		Account a1 = new Account(Account.AccountType.SAVINGS);
		assertEquals(oscar.checkOwnAccounts(a1), false);
	}

	/**
	 * Test the own account method when the customer doesen't own some accounts.
	 */
	@Test
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

	/**
	 * Test transferring all funds between accounts.
	 */
	@Test
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

	/**
	 * Test transferring some funds between accounts.
	 */
	@Test
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

	/**
	 * Test transferring funds when don't have required funds.
	 */
	@Test
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

	/**
	 * Test transferring funds when don't own from account.
	 */
	@Test
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

	/**
	 * Test transferring funds when don't own to account.
	 */
	@Test
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

	/**
	 * Test calculating all accounts holdings.
	 */
	@Test
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

	/**
	 * Test toString method.
	 */
	@Test
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
