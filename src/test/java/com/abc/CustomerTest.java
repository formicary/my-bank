package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {

	@Test // Test customer statement generation
	public void testApp() {
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();
		Customer henry = new Customer("Henry");
		henry.openAccount(checkingAccount);
		henry.openAccount(savingsAccount);
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);
		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n" + "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n"
				+ "Total $3,800.00\n" + "\n" + "Total In All Accounts $3,900.00", henry.printStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new SavingsAccount());
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		assertEquals(2, oscar.getNumberOfAccounts());
	}
}
