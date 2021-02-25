package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private String c = "CHECKING";
	private String s = "SAVEINGS";
	private String m = "MAXISAVEINGS";

	@Test // Test customer statement generation
	public void testApp() {

		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();
		Account maxiSavingsAccount = new MaxiSavingsAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
				.openAccount(maxiSavingsAccount);

		checkingAccount.deposit(100.0, c);
		savingsAccount.deposit(4000.0, s);
		savingsAccount.withdraw(200.0, s);
		maxiSavingsAccount.deposit(3000.0, m);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
				+ "\n" + "Maxi Savings Account\n" + "  deposit $3,000.00\n" + "Total $3,000.00\n" + "\n"
				+ " Total In All Accounts $6,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccounts() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAccounts() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		oscar.openAccount(new MaxiSavingsAccount());
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTransferAccounts() {
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer joe = new Customer("Joe").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0, c);
		savingsAccount.deposit(4000.0, s);

		assertEquals("Transfer completed", joe.transferBetweenAccounts(checkingAccount, savingsAccount, 500));
		assertEquals(500.0, checkingAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(4500.0, savingsAccount.getBalance(), DOUBLE_DELTA);
	}

	@Test
	public void testTransferFailed() {
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer joe = new Customer("Joe").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0, c);
		savingsAccount.deposit(4000.0, s);

		assertEquals("amount must be greater than zero, accounts must be exists.",
				joe.transferBetweenAccounts(checkingAccount, savingsAccount, 0));
		assertEquals(1000.0, checkingAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(4000.0, savingsAccount.getBalance(), DOUBLE_DELTA);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testdepositFailed() {
		Account checkingAccount = new CheckingAccount();
		Customer henry = new Customer("Henry").openAccount(checkingAccount);
		checkingAccount.deposit(0.0, c);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testdwithdrawFailed() {
		Account checkingAccount = new CheckingAccount();
		Customer henry = new Customer("Henry").openAccount(checkingAccount);
		checkingAccount.deposit(100.0, c);
		checkingAccount.withdraw(200.0, c);
	}
}
