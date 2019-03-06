package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Bank.
 */
public class Bank {
	
    private List<Customer> customers;

    /**
     * Instantiates a new bank.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer to the bank.
     *
     * @param customer the customer
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * gives a summary of different customers in the bank.
     *
     * @return the string
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Formats the word, mainly account to make the word plural if the number fed in is greater than 1
     * @param number of words
     * @param word to format
     * @return the formatted string
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Total interest paid to the customers from their accounts.
     *
     * @return the interest
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Gets the first customer from the bank.
     *
     * @return the first customer
     */
    public String getFirstCustomer() {
        if(customers.isEmpty()) {
        	throw new IndexOutOfBoundsException("No customers found belonging to this bank");
        }
        
        return 
        	customers.get(0).getName();

    }

	/**
	 * Gets the list of customers for this bank.
	 *
	 * @return the customers list
	 */
	public List<Customer> getCustomers() {
		return customers;
	}
    
}
