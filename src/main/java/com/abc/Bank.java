package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer not provided");
        }

        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append("\n - ")
                .append(c.getName())
                .append(" (")
                .append(format(c.getNumberOfAccounts(), "account"))
                .append(')');
        }
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public void updateInterestPayments() {
        for (Customer c : customers) {
            c.updateInterestPayments();
        }
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }
}
