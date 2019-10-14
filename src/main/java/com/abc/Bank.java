package com.abc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Author: Nicholas Hill
Email: nicholasjhill@live.co.uk
Mobile Number: 07763047785
Website: nicholasjhill.com
LinkedIn: linkedin.com/in/nicholas-j-hill/*/

public class Bank {

	private String name;
	// List of employees (tellers or bank managers)
	private List<Employee> employees;
	// Map of customers and their accounts
	private Map<Customer, List<Account>> customers;

	public Bank(String name) {
		this.name = name;

		employees = new ArrayList<Employee>();
		customers = new HashMap<Customer, List<Account>>();
	}

	// The bank can be instantiated with one manager and one teller
	public Bank(String name, BankManager bankManager, Teller teller) {
		this(name);

		addEmployee(bankManager);
		addEmployee(teller);
	}

	// The bank can also be instantiated with multiple bank manager and tellers
	public Bank(String name, List<Employee> bankManagers, List<Employee> tellers) {
		this(name);

		addEmployees(bankManagers);
		addEmployees(tellers);
	}

	// Add a single employee to the bank
	public void addEmployee(Employee employee) {
		employees.add(employee);
		employee.setBank(this);
	}

	// Add multiple employees to the bank
	public void addEmployees(List<Employee> employees) {
		for (Employee employee : employees)
			employee.setBank(this);

		this.employees.addAll(employees);
	}

	// Checks whether the employee works for the bank
	public boolean isEmployee(Employee employee) {
		for (Employee e : employees)
			if (e.equals(employee)) {
				return true;
			}

		return false;
	}

	// Checks whether the manager is a manager for the bank
	public boolean isManager(BankManager bankManager) {
		for (Employee employee : employees)
			if (employee.equals(bankManager)) {
				return true;
			}

		return false;
	}

	// Add a customer and their initial account to the bank
	public void addCustomer(Customer customer, Account account) {
		List<Account> accounts = new ArrayList<Account>();
		accounts.add(account);

		customers.put(customer, accounts);
	}

	// Checks whether the customer already has an account with the bank
	public boolean isCustomer(Customer customer) {
		return customers.containsKey(customer);
	}

	// Get an account from a given account number
	public Account getAccount(int accountNumber) {
		for (Account account : getAccounts())
			if (account.getAccountNumber() == accountNumber) {
				return account;
			}

		return null;
	}

	// Gets a list of the accounts for a customer
	public List<Account> getAccounts(Customer customer) {
		for (Map.Entry<Customer, List<Account>> c : customers.entrySet()) {
			if (c.getKey().equals(customer)) {
				return c.getValue();
			}
		}

		return null;
	}

	// Adds a new account for a customer
	public void addAccount(Customer customer, Account account) {
		customers.get(customer).add(account);
	}

	// Get all accounts
	public List<Account> getAccounts() {
		List<Account> accounts = new ArrayList<Account>();

		for (Map.Entry<Customer, List<Account>> customer : customers.entrySet()) {
			accounts.addAll(customer.getValue());
		}

		return accounts;
	}

	public String getName() {
		return name;
	}

	// Created for JUnit testing purposes
	public List<Employee> getEmployees() {
		return employees;
	}

	public Map<Customer, List<Account>> getCustomers() {
		return customers;
	}

	// Created for JUnit testing purposes
	public void setCustomers(Map<Customer, List<Account>> customers) {
		this.customers = customers;
	}

}
