package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to represent Bank objects. A bank has a list of customers and can
 * output reports.
 * 
 * @author JT
 *
 */
public class Bank {
	private List<Customer> customers;

	/**
	 * A constructor for the bank object which initialises a list of customers.
	 */
	public Bank() {
		customers = new ArrayList<Customer>();
	}

	/**
	 * A method to add a customer to the bank.
	 * 
	 * @param customer
	 *            The Customer object that will be added to the bank.
	 */
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	/**
	 * A method to output a customer summary for the bank.
	 * 
	 * @return A String that contains information on each customer and the number of
	 *         accounts they have.
	 */
	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	/**
	 * A method to format a string.
	 * 
	 * @param number
	 *            An int value used to determine if the following word is plural.
	 * @param word
	 *            The base word without any plural modifiers.
	 * @return A String of the number and then the word with corresponding plural
	 *         modifiers.
	 */
	// Make sure correct plural of word is created based on the number passed in:
	// If number passed in is 1 just return the word otherwise add an 's' at the end
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	/**
	 * A method to find the total interest paid accross all accounts.
	 * 
	 * @return A double value of the total interest paid on all accounts.
	 */
	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	/**
	 * A method to get the first customer added to the bank.
	 * 
	 * @return A String which corresponds to the name of the first customer in the
	 *         banking system.
	 */
	public String getFirstCustomer() {
		try {
			return customers.get(0).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}
}
