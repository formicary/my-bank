package com.abc;

import java.util.*;

public class Bank {
	// list of customers 
    private List<Customer> customers;

    protected Bank() {
        customers = new ArrayList<Customer>();
    }
    
    // adding customers to list
    protected void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    // to get the summary of all customers and the number of accounts they hold 
    protected String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    protected String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    
    // get the total interest paid by the bank on all accounts
    protected double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    
    // to get the name of the first customer 
    public String getFirstCustomer() {
        try {
            //customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
