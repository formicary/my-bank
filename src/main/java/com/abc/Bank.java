package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Customer> customers;

	public Bank () {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer (Customer customer) {
		customers.add(customer);
	}

	public String customerSummary () {
		String summary = "Customer Summary";
		for (Customer c : customers) {
			int n = c.getNumberOfAccounts();
			summary += "\n - " + c.getName() + " (" + n + format(n, " account") + ")";
		}
		return summary;
	}

	private String format (int number, String word) {
		return (number <= 1 ? word : word + "s");
	}

	public double totalInterestPaid () {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}
}
