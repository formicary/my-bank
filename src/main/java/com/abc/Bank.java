package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
* The Bank class represents a Bank, that can have customers,
* display customer summaries, and calculate interest paid.
*
* @author  Matthew Mukalere
* @version 0.5
*/
public class Bank {
    private List<Customer> customers;

    /**
     * Bank Class constructor.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer to the bank.
     * @param customer The first customer to add to the bank
     * @return customer The customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Gets a summary of the customer accounts.
     * @return summary a string containing the customer account summary
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Pluralises a word by adding an 's', based on whether the
     * number is not the number 1.
     * @param number The number
     * @param word The word
     * @return formattedWord A string containing the formatted word
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculates the total interest paid.
     * @return total Total interest paid
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Gets the first customer added to the bank.
     * @return customerName A string containing the formatted word
     */
    public String getFirstCustomer() {
    	if(customers.size() > 0) {
    		return customers.get(0).getName();
    	}
    	return "";    	
    }
}
