package com.abc;

import java.util.List;

/**
 * Creates a Summary report of all {@link Customer}s' {@link Account}s.
 */
public class BankStatement implements IReport {

	private final List<Customer> customers;

	/**
	 * Constructs a new {@link BankStatement}.
	 * 
	 * @param customers
	 *            The {@link Customer}s of the {@link Bank}.
	 */
	public BankStatement(List<Customer> customers) {
		this.customers = customers;
	}

	public String build() {
		String summary = "Customer Summary";
		for (Customer customer : customers) {
			summary += "\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")";
		}
		return summary;
	}

	// Make sure correct plural of word is created based on the number passed in:
	// If number passed in is 1 just return the word otherwise add an 's' at the end
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}
}
