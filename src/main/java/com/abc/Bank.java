package com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a application that simulate the basic bank account business functions
 * 
 * @author others and Fei
 * 
 */
public class Bank {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	/**
	 * This methods return the account number summary for all existing user that
	 * registered in the bank.
	 * 
	 * @return String Customer Summary as a string
	 */
	public String customerSummary() {
		// Use StringBuffer for better performance than concatenation.
		StringBuffer str = new StringBuffer("Customer Summary");
		for (Customer c : customers) {
			str.append("\n - " + c.getName() + " ("
					+ format(c.getNumberOfAccounts(), "account") + ")");
		}
		
		return str.toString();
	}

	// Make sure correct plural of word is created based on the number passed
	// in:
	// If number passed in is 1 just return the word otherwise add an 's' at the
	// end
	private String format(int number, String word) {
		return number + " " + (number <= 1 ? word : word + "s");
	}

	/**
	 * Calculate the total interest paid by the bank. Including all users and
	 * all the accounts for each user. only keep two digits after decimal point
	 * 
	 * @return double This is the total interest paid by bank until today.
	 */
	public double totalInterestPaid() {
		double total = 0;
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
        for (Customer c : customers){
			total += c.totalInterestEarned();
		}
		return Double.valueOf(twoDecimals.format(total));
	}

}
