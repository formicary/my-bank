package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<Customer> customers;
	private ScheduledTask scheduledTask;

	public Bank() {
		customers = new ArrayList<Customer>();
		scheduledTask = new ScheduledTask();
		scheduledTask.startScheduleTask(customers);
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	public String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	public String getFirstCustomer() {
		if (!customers.isEmpty()) {
			return customers.get(0).getName();
		} else {
			return "Haven't got any customer";
		}
	}
}
