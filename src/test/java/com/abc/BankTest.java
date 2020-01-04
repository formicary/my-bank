package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	// Check if the summary for a customer with 1 account is correct.
	public void customerSummarySingularTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John", "Barber", 1);
		john.openAccount("CHECKING");
		bank.addCustomer(john);
		assertEquals("Customer Summary\n - John Barber (1 account)", bank.getCustomerSummary());
	}

	@Test
	// Check if the summary for a customer with 2 accounts is correct.
	public void customerSummaryPluralTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John", "Barber", 1);
		john.openAccount("CHECKING");
		bank.addCustomer(john);
		john.openAccount("CHECKING");
		assertEquals("Customer Summary\n - John Barber (2 accounts)", bank.getCustomerSummary());
	}

	@Test
	// Check if the interest for a CHECKING account is correctly calculated.
	public void checkingAccountTest() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill", "Barber", 2);
		bill.openAccount("CHECKING");
		bank.addCustomer(bill);
		bill.getAccounts().get(0).deposit(100.0);

		assertEquals(0.1, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	// Check if the interest for a SAVINGS account is correctly calculated.
	public void savings_accountTest() {
		Bank bank = new Bank();
		Customer joe = new Customer("Joe", "Barber", 2);
		joe.openAccount("SAVINGS");
		bank.addCustomer(joe);
		joe.getAccounts().get(0).deposit(1500.0);
		assertEquals(2.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	// Check if the interest for a MAXI SAVINGS account is correctly calculated.
	public void maxi_savings_accountTest() {
		Bank bank = new Bank();
		Customer gil = new Customer("Gil", "Barber", 3);
		gil.openAccount("MAXI SAVINGS");
		bank.addCustomer(gil);
		gil.getAccounts().get(0).deposit(3000.0);
		assertEquals(3.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	// Check if the total interest paid by a bank with multiple customers is
	// correct.
	public void getTotalInterestPaidTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John", "Barber", 1);
		john.openAccount("CHECKING");
		bank.addCustomer(john);
		john.getAccounts().get(0).deposit(100.0);
		Customer john2 = new Customer("John2", "Barber", 2);
		john2.openAccount("CHECKING");
		bank.addCustomer(john2);
		john2.getAccounts().get(0).deposit(100.0);
		assertEquals(0.2, bank.getTotalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	// Check if the first customer is correctly returned.
	public void getFirstCustomerTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John", "Barber", 1);
		john.openAccount("CHECKING");
		bank.addCustomer(john);
		john.getAccounts().get(0).deposit(100.0);
		Customer john2 = new Customer("John2", "Barber", 2);
		john2.openAccount("CHECKING");
		bank.addCustomer(john2);
		john2.getAccounts().get(0).deposit(100.0);
		assertEquals("John Barber", bank.getFirstCustomer());
	}

	@Test
	// Check if the list of customers is correctly returned.
	public void getCustomersTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John", "Barber", 1);
		bank.addCustomer(john);
		Customer john2 = new Customer("John2", "Barber", 2);
		bank.addCustomer(john2);
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(john);
		customers.add(john2);
		assertEquals(customers, bank.getCustomers());
	}
}
