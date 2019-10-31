package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test // Test customer statement generation
	public void testApp() {

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
	public void testDeposite() {
		Customer oscar = new Customer("Oscar");
		Account savingsAccount = new Account(Account.SAVINGS);
		oscar.openAccount(savingsAccount);
		savingsAccount.deposit(1500);
		assertEquals(1500.00, savingsAccount.sumTransactions(), DOUBLE_DELTA);
	}

	@Test
	public void testWithdraw() {
		Customer oscar = new Customer("Oscar");
		Account savingsAccount = new Account(Account.SAVINGS);
		oscar.openAccount(savingsAccount);
		savingsAccount.withdraw(1500);
		assertEquals(-1500.00, savingsAccount.sumTransactions(), DOUBLE_DELTA);
	}

	@Test
	public void testDepositeAndWithdraw() {
		Customer oscar = new Customer("Oscar");
		Account savingsAccount = new Account(Account.SAVINGS);
		oscar.openAccount(savingsAccount);
		savingsAccount.deposit(1500);
		savingsAccount.withdraw(150);
		savingsAccount.deposit(50);
		savingsAccount.withdraw(500);
		assertEquals(900.00, savingsAccount.sumTransactions(), DOUBLE_DELTA);
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(Account.SAVINGS));
		oscar.openAccount(new Account(Account.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar");
		oscar.openAccount(new Account(Account.SAVINGS));
		oscar.openAccount(new Account(Account.CHECKING));
		oscar.openAccount(new Account(Account.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}
	
	@Test
	public void testTransfer() throws IOException {
		Customer oscar = new Customer("Oscar");
		Account savings = new Account(Account.SAVINGS);
		oscar.openAccount(savings);
		savings.deposit(1000);
		Account checking = new Account(Account.CHECKING);
		oscar.openAccount(checking);
		oscar.transferBetweenAccounts(savings, checking, 300);
		assertEquals(300.0, checking.sumTransactions(), DOUBLE_DELTA);
		assertEquals(700.0, savings.sumTransactions(), DOUBLE_DELTA);
		
	}
	
}
