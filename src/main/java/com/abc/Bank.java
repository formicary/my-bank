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
		return new BankStatement(customers).build();
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
		for (Customer customer : customers) {
			total += customer.totalInterestEarned();			
		}
		return total;
	}
}
