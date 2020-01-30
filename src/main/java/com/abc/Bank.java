package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Bank {
	
    private List<Customer> customers;
    
    //A Bank is created with an empty list of customers.
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    //Add customers to the Bank.
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    //Generate a summary with all existing customers and the number of accounts they own.
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
        	int n = c.getNumberOfAccounts();
            summary += "\n - " + c.getName() + " (" + n + " " + (n == 1 ? "account" : "accounts") + ")";
        }
        return summary;
    }
    
    //Calculate the total interest paid to all accounts.
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers) {
        	for (Account a : c.getAccounts()) {
                total += a.interestEarned();
        	}
        }
        return total;
    }

}