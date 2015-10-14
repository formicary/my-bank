package test.java.com.abc;

//import java.util.Calendar;

import main.java.com.abc.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.Type.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)",
				bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.Type.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.Type.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.Type.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000.0);
		assertEquals(3, bank.totalInterestPaid(), DOUBLE_DELTA);

		// Testing maxi-saving interest for more than 10 days
		/*
		 * checkingAccount.getTransaction(0).setDay(
		 * Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 20);
		 * assertEquals(150, bank.totalInterestPaid(), DOUBLE_DELTA);
		 */
	}

}
