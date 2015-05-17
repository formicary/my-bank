package com.abc;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds an abstraction of a commercial bank.
 * 
 * @author Donald Campbell (campbell.donald@gmail.com)
 *
 */
public class Bank {
    private Map<Integer, Customer> customers;

    /**
     * Standard constructor.
     */
    public Bank() {
        customers = new HashMap<Integer, Customer>();
    }

    /** 
     * Register a new customer, given a customer name and unique identification number.
     * @param customerName The customer's human-readable name.
     * @param uniqueIDNumber The customer's unique identification number.
     * @return An instance of the Customer class representing the newly-registered customer.
     */
    public Customer registerNewCustomer(String customerName, int uniqueIDNumber) {
        Customer newCustomer = new Customer(customerName, uniqueIDNumber);
        customers.put(uniqueIDNumber, newCustomer);
        
        return newCustomer;
    }
    
    /**
     * Returns a customer class instance given the customer's unique ID.
     * @param uniqueIDNumber The unique ID of the desired customer.
     * @return A customer class instance representing the requested customer, or null if the customer's unique ID is not registered with the bank.
     */
    public Customer getCustomerByUniqueID(int uniqueIDNumber) {
    	if (customers.containsKey(uniqueIDNumber)) {
    		return customers.get(uniqueIDNumber);
    	} else {
    		return null;
    	}
    }

    /**
     * Returns a summary of all customers registered with the bank, and the number of accounts each customer holds.
     * @return A string representing the customer summary.
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        
        for (Customer c : customers.values())
            summary += "\n - " + c.getName() + " (" + HelperFunctions.format(c.getNumberOfAccounts(), "account") + ")";
        
        return summary;
    }

    /**
     * Calculates the total interest paid on all accounts as of the current system date.
     * @return The total interest the bank has paid on all customer accounts, as of the current system date.
     */
    public double totalInterestPaid() {
    	return totalInterestPaid(DateProvider.getInstance().now());
    }
    
    /**
     * Calculates the total interest paid on all accounts as of a given reference date.
     * @param asOf The current reference date.
     * @return The total interest the bank has paid on all customer accounts, as of the given reference date.
     */
    public double totalInterestPaid(Date asOf) {
        double total = 0;
        
        for (Customer c: customers.values()) {
            total += c.totalInterestEarned(asOf);
        } 
        
        return total;
    }
}
