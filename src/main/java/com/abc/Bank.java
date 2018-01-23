package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    //constructor
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    //method to add a new customer
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //method to retrieve a summary of a specific customer
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

    //method to calculate the total interest paid for all the customers
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    //method to retrieve the first customer
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error, no customers added to the Bank";
        }
    }
}
