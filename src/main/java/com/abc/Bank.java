package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    //Initialise new bank and customer list
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    //add a customer to the customer list
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //Get a summary of the customer list and how many accounts they have
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    //calculate total interest 
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public void dayRollover() {	//Would be called at the beginning of each day
    	for(Customer c: customers)
            c.updateInterestForAccounts();
    }
    
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
