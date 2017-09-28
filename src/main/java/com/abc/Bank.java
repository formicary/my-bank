package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    
    /**
     * constructor creates a new list of customers
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * adds a customer to the list
     * @param customer 
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    /**
     * 
     * @return a string of the customers details
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer customer : customers)
            summary += "\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        
        return number + " " + (number == 1 ? word : word + "s");
    }
    
    /**
     * 
     * @return the total interest paid
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }
    
    /**
     * 
     * @return first customer in the list 
     */
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error getting first customer";
        }
    }
}
