package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * BankTest class test customer and account creation for customer. It tests if
 * the interest earned for various type of accounts are computed correctly as
 * per defined interest rates and rules
 * 
 * @version 2.0 03 July 2019
 * @updated by Dhurjati Dasgupta
 */

public class BankTest {
	private static final double DOUBLE_DELTA = 0.5e-2; // changed this to check 2 decimal places for checking amounts

	@Test
	/*Tests customer and account creation for a customer */
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	/* Tests interest is calculated correctly for CHECKING type of account*/
	public void checkingAccount() {
		/**
		 * Creates CHECKING type of account for a customer, deposits amount, moves date forward
		 * by 365 days and tests if the interest is calculated correctly for 365 days
		 */
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.CHECKING);
		Customer bill = new Customer("John").openAccount(checkingAccount);
		bank.addCustomer(bill);
		DateProvider.getInstance();

		DateProvider.setDateForward(0);

		checkingAccount.deposit(100.0);

		DateProvider.setDateForward(365);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	/* Tests interest is calculated correctly for SAVINGS type of account */
	public void savings_account() {
		/**
		 * Creates SAVINGS type of account for a customer, deposits amount, moves date
		 * forward by 365 days and tests if the interest is calculated correctly for 365
		 * days
		 */
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.SAVINGS);
		bank.addCustomer(new Customer("John").openAccount(checkingAccount));
		DateProvider.getInstance();
		DateProvider.setDateForward(0);

		checkingAccount.deposit(1500.0);
		DateProvider.setDateForward(365);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	/* Tests interest is calculated correctly for MAXI_SAVINGS type of account*/
	public void maxi_savings_account() {
		/**
		 * Creates MAXI_SAVINGS type of account for a customer, deposits amount, moves date forward
		 * by 365 days and tests if the interest is calculated correctly for 365 days
		 */
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("John").openAccount(checkingAccount));

		DateProvider.getInstance();
		DateProvider.setDateForward(0);

		checkingAccount.deposit(3000.0);

		DateProvider.setDateForward(365);

		assertEquals(153.8, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

}
