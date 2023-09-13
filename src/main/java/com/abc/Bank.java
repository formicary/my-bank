package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers = new ArrayList<>();

    /**
     * Adds a new Customer to Bank
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * prints a report showing the list of customers and how many accounts they have
     */
    public String printCustomerSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");
        for (Customer c : customers)
            summary.append(System.lineSeparator())
                    .append(" - ")
                    .append(c.getName()).append(" (")
                    .append(c.getAccounts().size())
                    .append(c.getAccounts().size()==1?" account":" accounts")
                    .append(")");
        return summary.toString();
    }

    /**
     * prints a report showing the total interest paid by the bank on all accounts
     */
    public double calculateTotalInterestsPaid() {
        double total = 0;
        for(Customer c: customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }
}
