package com.abc.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {

	private final List<Customer> customerList;

	public Bank() {
		customerList = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customerList.add(customer);
	}

	public Money totalInterestPaid() {
		Money total = Money.ZERO_USD;
		for (Customer customer : customerList)
			total = total.plus(customer.totalInterestEarned());
		return total;
	}

	public List<Customer> getCustomers() {
		return Collections.unmodifiableList(customerList);
	}

	public Customer getFirstCustomer() {
		if (customerList.isEmpty()) {
			return null;
		}

		return customerList.iterator().next();
	}

}
