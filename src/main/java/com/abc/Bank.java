package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bank {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public String customerSummary() {
		StringBuilder sb = new StringBuilder("Customer Summary");
		customers.forEach(c -> {
			sb.append("\n - ");
			sb.append(c.getSummary());
		});
		return sb.toString();
	}

	public double totalInterestPaid() {
		return customers.stream().map(Customer::totalInterestEarned)
				.collect(Collectors.summingDouble(Double::doubleValue));
	}

}
