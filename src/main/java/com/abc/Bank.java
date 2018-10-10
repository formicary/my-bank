package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
//Bank constuctor
    public Bank() {
        this.customers = new ArrayList<Customer>();
    }
//method to add a new customer
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }
//method to get a summary of customers
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : this.customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
//method to calculate the total Interest Paid
    public double totalInterestPaid(int numOfDays) {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned(numOfDays);
        return total;
    }
//method to get the first customer
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
