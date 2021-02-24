package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test // Test customer statement generation
	public void testApp() {

		Account checkingAccount = new Account(Account.CHECKING);
		Account savingsAccount = new Account(Account.SAVINGS);
		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
				.openAccount(maxiSavingsAccount);

		checkingAccount.deposit(100.0, 0);
		savingsAccount.deposit(4000.0, 1);
		savingsAccount.withdraw(200.0, 1);
		maxiSavingsAccount.deposit(3000.0, 2);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
				+ "\n" + "Maxi Savings Account\n" + "  deposit $3,000.00\n" + "Total $3,000.00\n" + "\n"
				+ " Total In All Accounts $6,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccounts() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
		oscar.openAccount(new Account(Account.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAccounts() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
		oscar.openAccount(new Account(Account.CHECKING));
		oscar.openAccount(new Account(Account.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTransferAccounts() {
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingsAccount = new Account(Account.SAVINGS);

		Customer joe = new Customer("Joe").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0, 0);
		savingsAccount.deposit(4000.0, 1);

		assertEquals("Transfer completed", joe.transferBetweenAccounts(checkingAccount, savingsAccount, 500));
		assertEquals(500.0, checkingAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(4500.0, savingsAccount.getBalance(), DOUBLE_DELTA);
	}

	@Test
	public void testTransferFailed() {
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingsAccount = new Account(Account.SAVINGS);

		Customer joe = new Customer("Joe").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0, 0);
		savingsAccount.deposit(4000.0, 1);

		assertEquals("amount must be greater than zero, accounts must be exists.",
				joe.transferBetweenAccounts(checkingAccount, savingsAccount, 0));
		assertEquals(1000.0, checkingAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(4000.0, savingsAccount.getBalance(), DOUBLE_DELTA);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testdepositFailed() {
		Account checkingAccount = new Account(Account.CHECKING);
		Customer henry = new Customer("Henry").openAccount(checkingAccount);
		checkingAccount.deposit(0.0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testdwithdrawFailed() {
		Account checkingAccount = new Account(Account.CHECKING);
		Customer henry = new Customer("Henry").openAccount(checkingAccount);
		checkingAccount.deposit(100.0, 0);
		checkingAccount.withdraw(200.0, 0);
	}
}
