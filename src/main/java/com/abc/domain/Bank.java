package com.abc.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Bank.
 */
public class Bank {

    private final List<Customer> customers = new ArrayList<>();

    /**
     * Registers a new customers.
     *
     * @param customer the customer.
     */
    public void addCustomer(final Customer customer) {
        customers.add(customer);
    }

    /**
     * Provides a summary of all customers.
     * This report shows the list of customers and how many accounts they have.
     *
     * @return The textual summary.
     */
    public String customersSummary() {
        final StringBuilder summaryBuilder = new StringBuilder();
        summaryBuilder.append("Customer Summary");
        for (final Customer c : customers) {
            summaryBuilder.append("\n - ");
            summaryBuilder.append(c.getShortDescription());
        }
        return summaryBuilder.toString();
    }

    /**
     * @return Total interest paid by the bank.
     */
    public double totalInterestPaid() {
        double total = 0;
        for (final Customer c : customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

}
