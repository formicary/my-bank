package com.abc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankTest {

	private static Bank abcBank;
	private static Customer customerMatt;

	@BeforeClass
	public static void init() {
		Teller tellerAlison = new Teller("Alison");
		BankManager managerRobert = new BankManager("Robert");

		// Instantiate a bank with a teller and bank manager
		abcBank = new Bank("ABC Bank", managerRobert, tellerAlison);

		customerMatt = new Customer("Matt");

		// Make accounts for both customers
		// Account number: 0
		tellerAlison.openAccount(customerMatt, 0);
	}

	@Test
	public void addMultipleEmployees() {
		Teller tellerLuna = new Teller("Luna");
		Teller tellerMolly = new Teller("Molly");
		BankManager managerPatricia = new BankManager("Patricia");

		List<Employee> employees = new ArrayList<Employee>();

		employees.add(tellerLuna);
		employees.add(tellerMolly);
		employees.add(managerPatricia);

		abcBank.addEmployees(employees);

		// Check if all the employees are present in the bank's employee list
		assertTrue(abcBank.getEmployees().containsAll(employees));
	}

	@Test
	public void nonExistentAccountNumber() {
		// The bank should return null if a non-existent account number is passed
		assertNull(abcBank.getAccount(3));
	}

	@Test
	public void getAccountByNumber() {
		// The bank should return the account via the passed account number (account
		// number: 0)
		assertNotNull(abcBank.getAccount(0));
		assertTrue(abcBank.getAccount(0).getAccountNumber() == 0);
	}

	@Test
	public void getAccountsForCustomer() {
		// The bank should return all accounts belonging to the customer
		assertTrue(abcBank.getAccounts(customerMatt).size() == 1);
		assertTrue(abcBank.getAccounts(customerMatt).get(0).getAccountNumber() == 0);
		assertNotNull(abcBank.getAccounts(customerMatt));
	}

	@AfterClass
	public static void finalise() {
		abcBank = null;
		customerMatt = null;
	}

}
