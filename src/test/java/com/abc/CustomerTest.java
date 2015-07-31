package com.abc;

import main.java.com.abc.*;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class CustomerTest {

	@Test
	// Test customer statement generation
	public void testApp() {

		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount)
				.openAccount(savingsAccount);

		checkingAccount.deposit(10000000.12);
		savingsAccount.deposit(4000.04);
		savingsAccount.withdraw(200.03);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n"
				+ "  deposit $10,000,000.12\n" + "Total $10,000,000.12\n"
				+ "\n" + "Savings Account\n" + "  deposit $4,000.04\n"
				+ "  withdrawal $200.03\n" + "Total $3,800.01\n" + "\n"
				+ "Total In All Accounts $10,003,800.13", henry.getStatement());
	}

	@Test
	public void testInsufficientFund() {

		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount)
				.openAccount(savingsAccount);

		savingsAccount.deposit(4000.04);

		try {
			savingsAccount.withdraw(4200.03);
			fail("not sufficient fund into the account");
		} catch (Exception e) {
		}
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar")
				.openAccount(new SavingsAccount());
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar")
				.openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar")
				.openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		oscar.openAccount(new MaxiSavingsAccount());
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testLast10DaysWithdrawn() {

		Account checkingAccount = new CheckingAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount)
				.openAccount(checkingAccount);

		checkingAccount.deposit(4000.04);
		checkingAccount.withdraw(100);
		assertEquals(true, checkingAccount.hasWithdrawnInLast10Days());
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void negativeDepositeTest() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be greater than zero");
		Account checkingAccount = new CheckingAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount)
				.openAccount(checkingAccount);
		checkingAccount.deposit(-4000.04);

	}

	@Test
	public void insufficientWithdrawlAmountTestException() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("not sufficient fund into the account");
		Account checkingAccount = new CheckingAccount();

		Customer henry = new Customer("Henry").openAccount(checkingAccount)
				.openAccount(checkingAccount);
		checkingAccount.withdraw(4000.04);
	}
}
