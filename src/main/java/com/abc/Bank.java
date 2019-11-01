package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	// returns each customer and the number of accounts they have
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

	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	// same as above but calls the function set one year in the future
	public double totalInterestPaidTest() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarnedTest();
		return total;
	}

	public String getFirstCustomer() {
		try {
			// customers = null; removed as it would always fail
			// System.out.println(customers.get(0).getName());
			return customers.get(0).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}
}
