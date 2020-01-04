package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;

	@Test // Test customer statement generation
	public void testApp() {
		Customer henry = new Customer("Henry", "Jr", 4);
		henry.openAccount("CHECKING");
		Account checkingAccount = henry.getAccounts().get(0);
		henry.openAccount("SAVINGS");
		Account savingsAccount = henry.getAccounts().get(1);
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);
		assertEquals("Statement for Henry Jr\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n"
				+ "Total $100.00\n" + "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n"
				+ "Total $3,800.00\n" + "\n" + "Total In All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar", "Hamilton", 5);
		oscar.openAccount("SAVINGS");
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar", "Hamilton", 5);
		oscar.openAccount("SAVINGS");
		oscar.openAccount("CHECKING");
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void interAccountsTransferTest() throws Exception {
		Customer henry = new Customer("Henry", "Jr", 4);
		henry.openAccount("CHECKING");
		Account checkingAccount = henry.getAccounts().get(0);
		henry.openAccount("SAVINGS");
		Account savingsAccount = henry.getAccounts().get(1);
		checkingAccount.deposit(1000.0);
		savingsAccount.deposit(4000.0);
		henry.interAccountsTransfer(checkingAccount, savingsAccount, 1000.0);
		assertEquals(5000.0, savingsAccount.getBalance(), DOUBLE_DELTA);
	}

}
