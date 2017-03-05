package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Bank {

	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	/**
	 * Add customer and associated account(s) to bank
	 * @param customer customer to add to bank
	 */
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	/**
	 * Remove customer and associated account(s) from bank
	 * @param customer customer to remove from bank
	 */
	public void removeCustomer(Customer customer) {
		if(customers.contains(customer)) {
			customers.remove(customer);
		}
	}
	/**
	 * Summary of customer names and number of associated accounts
	 * @return customer summary
	 */
	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : getCustomers())
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	/**
	 * Returns singular form of word if number equal to one.
	 * Otherwise, plural returned
	 * @param number quantity of noun
	 * @param word word to format
	 * @return formatted word
	 */
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}
	/**
	 * Calculate total amount of interest paid to customers
	 * @return interest paid
	 */
	public double totalInterestPaid() {
		double total = 0;
		for(Customer c: getCustomers())
			total += c.totalInterestEarned();
		return total;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

}
