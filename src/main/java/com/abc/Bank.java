package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	// List of customers in the bank.
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	// Add a customer to the bank list.
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	// Produce a list as a string with all the customers and their number of
	// accounts.
	public String getCustomerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers) {
			summary += "\n - " + c.getFirstName() + " " + c.getSurname() + " ("
					+ format(c.getNumberOfAccounts(), "account") + ")";
		}
		return summary;
	}

	// Make sure correct plural of word is created based on the number passed in.
	// If number passed in is 1 just return the word otherwise add an 's' at the
	// end.
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	// Calculate total interest paid by bank to customers.
	public double getTotalInterestPaid() {
		double total = 0.0;
		for (Customer c : customers) {
			total += c.getCustomerTotalInterestEarned();
		}
		return total;
	}

	// Return the first ever customer in the bank.
	public String getFirstCustomer() {
		try {
			Customer c = customers.get(0);
			return c.getFirstName() + " " + c.getSurname();
		} catch (Exception e) {
			return "The bank has no customers.";
		}
	}

	// Return the customers list.
	public List<Customer> getCustomers() {
		return customers;
	}

}
