package com.abc;

import java.util.LinkedList;

/**
 * Bank class adds customers into customer list, provides a summary report for
 * list of customers with number of accounts and total interest earned for all
 * customers
 * 
 * @version 2.0 03 July 2019
 * @updated by Dhurjati Dasgupta
 */

public class Bank {

	private LinkedList<Customer> customers;

	public Bank() {

		customers = new LinkedList<Customer>();
	}

	/* Add customers to the customer linked list */
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	/* Provides a summary report of how many accounts each customer has */
	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	/**
	 * Make sure correct plural of word is created based on the number passed in If
	 * number passed in is 1 just return the word otherwise add an 's' at the end
	 */
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	/* calculates total interest paid for all the customers */
	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	/* gets first customer name */
	public String getFirstCustomer() {
		try {
			customers = null;
			return customers.get(0).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}
}
