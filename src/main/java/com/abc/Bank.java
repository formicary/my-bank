package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append("\n - ");
            summary.append(c.getName());
            summary. append(" (");
            summary.append(format(c.getNumberOfAccounts(), "account"));
            summary.append(")");
        }
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        word = (number == 1) ? word : word + "s";
        return number + " " + word;
    }

    public double totalInterestPaid(boolean forYear) {
        double total = 0;
        for(Customer c : customers)
            total += c.totalInterestEarned(forYear);
        return total;
    }

    public String getFirstCustomer() {
        if(!customers.isEmpty())
            return customers.get(0).getName();
        return "No Customers";
    }
}
