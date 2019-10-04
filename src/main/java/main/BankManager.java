package main;

import java.util.List;

public class BankManager implements Comparable<BankManager> {

	private Bank bank;
	private List<Customer> customers;

	public BankManager(Bank b) {
		this.bank = b;
		customers = bank.getCustomers();
	}

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

	public String getFirstCustomerName() {
		if (customers.size() == 0 || customers == null) {
			return null;
		} else {
			return customers.get(0).getName();
		}
	}

	// Checks if two bank managers are equal.
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		BankManager other = (BankManager) obj;

		return this.bank == (other.bank);
	}

	// Allows two bank managers to be compared with each other.
	@Override
	public int compareTo(BankManager other) {
		return this.bank.compareTo(other.bank);
	}

	// Overriding the default toString representation of the BankManager class.
	@Override
	public String toString() {
		return "BankManager [bank=" + bank + ", customers=" + customers + "]";
	}

}
