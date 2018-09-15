package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all the {@link Customer}s of the bank.
 */
public class Bank {
	private List<Customer> customers;

	/**
	 * Constructs a new {@link Bank}.
	 */
	public Bank() {
		customers = new ArrayList<Customer>();
	}

	/**
	 * Adds a {@link Customer} to the {@link Bank}.
	 * 
	 * @param customer
	 *            The {@link Customer} to add.
	 */
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	/**
	 * Creates a Summary of all {@link Customer}s' {@link Account}s.
	 * 
	 * @return Summary of all customers and their accounts.
	 */
	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	// Make sure correct plural of word is created based on the number passed in:
	// If number passed in is 1 just return the word otherwise add an 's' at the end
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	/**
	 * Calculates the total interest paid by the {@link Bank} to all of the
	 * {@link Customer}s.
	 * 
	 * @return the total interest paid by the {@link Bank} to all of the
	 *         {@link Customer}s.
	 */
	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	/**
	 * Returns the first {@link Customer}.
	 * 
	 * @return See above.
	 */
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
