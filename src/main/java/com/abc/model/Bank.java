package com.abc.model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private final List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public Money totalInterestPaid() {
		Money total = Money.ZERO_USD;
		for (Customer customer : customers)
			total = total.plus(customer.totalInterestEarned());
		return total;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public Customer getFirstCustomer() {
		if (customers.isEmpty()) {
			return null;
		}

		return customers.iterator().next();
	}

}
