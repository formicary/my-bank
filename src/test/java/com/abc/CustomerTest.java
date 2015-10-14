package test.java.com.abc;

import main.java.com.abc.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerTest {

	@Test
	public void testApp() {

		Account checkingAccount = new Account(Account.Type.CHECKING);
		Account savingsAccount = new Account(Account.Type.SAVINGS);

		Customer henry = new Customer("Henry").openAccount(checkingAccount)
				.openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n"
				+ "\tdeposit $100.00\n" + "Total $100.00\n" + "\n"
				+ "Savings Account\n" + "\tdeposit $4,000.00\n"
				+ "\twithdrawal $200.00\n" + "Total $3,800.00\n" + "\n"
				+ "Total In All Accounts $3,900.00", henry.getStatement());
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(
				Account.Type.SAVINGS));
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(
				Account.Type.SAVINGS));
		oscar.openAccount(new Account(Account.Type.CHECKING));
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAcounts() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(
				Account.Type.SAVINGS));
		oscar.openAccount(new Account(Account.Type.CHECKING));
		oscar.openAccount(new Account(Account.Type.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTrasfer() {
		Customer oscar = new Customer("Oscar").openAccount(new Account(
				Account.Type.SAVINGS));
		oscar.openAccount(new Account(Account.Type.CHECKING));
		oscar.getAccount(0).deposit(1000);
		oscar.getAccount(1).deposit(3000);
		oscar.Transfer(1, 0, 1000);
		assertTrue(oscar.getAccount(0).getBalance() == oscar.getAccount(1)
				.getBalance());
		oscar.Transfer(1, 0, 5000);
		assertTrue(oscar.getAccount(0).getBalance() == oscar.getAccount(1)
				.getBalance());
	}
}
