package com.abc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Bank {
    private String name;
    private List<Customer> customers = new ArrayList<Customer>();
    
    public Bank(String name) {
    	this.name = name;
    }
    public Bank(String name, List<Customer> customers) {
    	this.name = name;
    	this.customers = customers;
    }
    
    public String getName() {
    	return name;
    }
    public void setName(String name) {
    	this.name = name;
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getFullName() + " (" + 
            	addSifPlural(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }
    
    private String addSifPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    // Calculate Total Interest Paid for each currency
    public String totalInterestPaid() {
    	HashMap<String, Double> interestDifferentCurrencies = new HashMap<String, Double>();
        for(Customer c: customers) {
        	HashMap<String, Double> interestsEarnedCustomer = c.totalInterestEarned();
        	for(Entry<String, Double> e : interestsEarnedCustomer.entrySet()) {
        		Double amountValue = interestDifferentCurrencies.getOrDefault(e.getKey(), 0.0);
        		interestDifferentCurrencies.put( e.getKey(), (amountValue + e.getValue()) );
            }
        }
        String statement = "\n"+"Total Interest Paid:\n";
        for(Entry<String, Double> e : interestDifferentCurrencies.entrySet()) {
        	statement += "- " + e.getKey() + e.getValue() + "\n";
        }
        return statement;
    }
    
    
    public Customer getCustomerByFullName(String fullName) {
        for (Customer c : customers) {
        	if (c.getFullName().equals(fullName)) {
        		return c;
        	}
        }
        return null;
    }
    
}
