package main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Class to represent a Bank
 * 
 * @author Stavros Mobile
 * 
 */
public class Bank {

	/**
	 * field to hold the date, used in runnable thread to check against change
	 * of date and apply interest to the accounts
	 */
	private int lastUpdateDate;

	/**
	 * A list of customers
	 */
	private List<Customer> customers;

	/**
	 * Constructor
	 */
	public Bank() {
		this.lastUpdateDate = DateProvider.getInstance().getDay();
		customers = new ArrayList<Customer>();
	}

	/**
	 * add new customer
	 * 
	 * @param customer
	 */
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	/**
	 * Get basic info of customers
	 * 
	 * @return
	 */
	public String customerSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " ("
					+ format(c.getNumberOfAccounts(), "account") + ")";
		return summary;
	}

	/**
	 * formats given word for plural
	 * 
	 * @param number
	 * @param word
	 * @return formatted word
	 */
	private String format(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	/**
	 * Calculate total interest from customers
	 * 
	 * @return total interest
	 */
	public double totalInterestPaid() {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
	}

	/**
	 * Runnable method that applies interest when day changes
	 * 
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException {

		if (this.lastUpdateDate != DateProvider.getInstance().getDay()) {

			// accounts for more than 1 days past
			int dayDif = DateProvider.getInstance().getDay()
					- this.lastUpdateDate;
			if (dayDif < 0)
				dayDif += 365;

			for (int c = 0; c < this.customers.size(); c++) {
				for (int a = 0; a < this.customers.get(c).getNumberOfAccounts(); a++) {
					double interest = (this.customers.get(c).getAccount(a)
							.interestEarned() / 365.)
							* dayDif;
					this.customers.get(c).getAccount(a).deposit(interest);
				}
			}
			this.lastUpdateDate = DateProvider.getInstance().getDay();
		}

		// update every hour?
		wait(3600000);
	}

}