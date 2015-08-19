package com.mybank;

import java.util.ArrayList;
import java.util.List;

public class Bank {

	private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String getCustomerSummary() {
    	String summary = null;
    	
    	if (customers.isEmpty())
    		summary = "There are currently no customers with active accounts";
        else {
	    	summary = "Customer Summary";
            //Display list of customers and number of accounts for each customer
	        for (Customer c : customers)
	            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double getTotalInterestPaid() {
        double total = 0;
    	if (!customers.isEmpty()) {
    		// Get total interest paid on all accounts for all customers
	        for(Customer c: customers)
	            total += c.getTotalInterestEarned();
    	}
        return total;
    }
}
