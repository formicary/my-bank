package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;

	Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));

	@Test 
	public void testStatementGen() {

		Account checkingAccount = new Account(Account.CHECKING);
		Account savingsAccount = new Account(Account.SAVINGS);

		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
				+ "\n" + "Total In All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		oscar.openAccount(new Account(Account.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTransferBetweenOwnedAccountsSuccess() {

		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		Account savingsAccount = new Account(Account.SAVINGS);
		oscar.openAccount(maxiSavingsAccount);
		oscar.openAccount(savingsAccount);

		savingsAccount.deposit(100.0);

		oscar.transferBetweenAccounts(savingsAccount, maxiSavingsAccount, 25.0);

		assertEquals(75.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
		assertEquals(25.0, maxiSavingsAccount.sumTransactions(), DOUBLE_DELTA);
	}
	

}
