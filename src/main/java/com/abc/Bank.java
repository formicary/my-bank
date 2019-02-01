package com.abc;

import java.util.ArrayList;
import java.util.List;

// A bank class, composed of a list of customers

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    // Returns a summary of all the customers for this bank
    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - ").append(c.getName()).append(" (").append(format(c.getNumberOfAccounts(), "account")).append( ")");
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }


    // Returns the total interest paid on the day for all customer accounts
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    }

