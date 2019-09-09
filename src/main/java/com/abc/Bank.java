package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<Customer> customers;

	/*
	 * Constructor
	 */
	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public String printCustomerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + Formatter.toPluralForm(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers) {
			total += c.totalInterestEarned();
			total = Double.valueOf(Formatter.toTwoDecimal(total));
		}
		return total;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public String getFirstCustomer() throws IndexOutOfBoundsException {
		if (customers.size() >= 1) {
			return customers.get(0).getName();
		} else {
			throw new IndexOutOfBoundsException("Bank does not have customers yet");
		}
	}
}
