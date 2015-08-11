package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	// @Ignore
	public void checkingAccount() {
		Bank bank = new Bank();
		Account testCheckingAccount = new CheckingAccount(100);
		Customer bill = new Customer("Czech King", 1)
				.openAccount(testCheckingAccount);
		bank.addCustomer(bill);

		testCheckingAccount.deposit(100.0);
		log("Total paid to Czech King " + bank.totalInterestPaid().toString());
		assertEquals(
				"\n\nCheckingAccount - interest not calculated properly\n\n",
				0.1, Double.valueOf(bank.totalInterestPaid().toString()),
				DOUBLE_DELTA);
	}

	@Test
	// @Ignore
	public void customerSummary() {
		Customer john = new Customer("John", 1);
		john.openAccount(new CheckingAccount(100));

		Bank bank = new Bank();
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)",
				bank.customerSummary());
	}

	private void log(String string) {
		System.out.println(string);
	}

	@Test
	// @Ignore
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account testMaxiAccount = new MaxiAccount(100);
		bank.addCustomer(new Customer("Max Savings", 1)
				.openAccount(testMaxiAccount));

		testMaxiAccount.deposit(3000.0);
		log("Total paid to Max Savings = " + bank.totalInterestPaid());

		assertEquals("\n\nMaxiAccount - interest not calculated properly\n\n",
				170.0, Double.valueOf(bank.totalInterestPaid().toString()),
				DOUBLE_DELTA);
	}

	@Test
	public void retrieveCustomer() {
		Customer donQuixote = new Customer("Don Quixote", 100);
		Customer donAldDuck = new Customer("Donald Duck", 100);
		Bank b = new Bank();
		b.addCustomer(donQuixote);
		b.addCustomer(donAldDuck);

		Customer donJuan = null;
		donJuan = b.getCustomer(100);

		assertEquals("Find function failed.", donQuixote, donJuan);
	}

	@Test
	// @Ignore
	public void savings_account() {
		Bank bank = new Bank();
		Account testSavingsAccount = new SavingsAccount(100);
		bank.addCustomer(new Customer("S. A. Vingh", 1)
				.openAccount(testSavingsAccount));

		testSavingsAccount.deposit(1500.0);

		log("Total paid to S. A. Vingh = " + bank.totalInterestPaid());

		assertEquals(
				"\n\nSavingsAccount - interest not calculated properly\n\n",
				2.0, Double.valueOf(bank.totalInterestPaid().toString()),
				DOUBLE_DELTA);
	}

	@Test
	// @Ignore
	public void testDailyInterest() {
		Account c1, s1, s2;
		Customer cam = new Customer("Cam Pound-Inchrest", 1);
		cam.openAccount(c1 = new CheckingAccount(1));
		cam.openAccount(s1 = new SavingsAccount(2));
		cam.openAccount(s2 = new SavingsAccount(3));

		cam.deposit(50000.00, 1);
		c1.deposit(50000.00);
		s1.deposit(100000.00);
		s2.deposit(200.00);

		MaxiAccount m1 = new MaxiAccount(100);
		cam.openAccount(m1);
		m1.deposit(100.00);

		m1.withdraw(100.00);
		m1.deposit(1000.00);

		Bank bank = new Bank();
		bank.addCustomer(cam);

		log(cam.getStatement());

		bank.accrueDailyInterest();

		// assertEquals("Customer Summary\n - John (1 account)",
		// bank.customerSummary());
	}

}
