package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    //Bank Constructor
    public Bank() {
        customers = new ArrayList<Customer>();
    }
    
    //Add a new customer to the Bank
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //Return list of customers along with number of accounts for each
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

    //Return the total amount of interest paid out to customers
    public double totalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }

    //Return Name of First Customer
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            return "No Customers";
        }
    }
}
