package com.abc;

import com.abc.utils.Formatting;

import java.util.ArrayList;
import java.util.List;

class Bank {
    private List<Customer> customers;

    Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a new customer to this bank.
     * @param customer the new customer
     */
    void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Generates a summary for all of the customers at this bank.
     * @return a summary of customers
     */
    String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");

        for (Customer c : customers) {
            int numAccounts = c.getNumberOfAccounts();

            summary.append("\n\t- ");
            summary.append(c.getName());
            summary.append(" (");
            summary.append(Formatting.pluralise(c.getNumberOfAccounts()));
            summary.append(")");
        }

        return summary.toString();
    }

    /**
     * Calculates the cumulative interest paid out to all bank customers.
     * @return the amount of interest paid
     */
    double totalInterestPaid() {
        double total = 0;

        for(Customer c: customers) total += c.totalInterestEarned();

        return total;
    }

    List<Customer> getCustomers() {
        return customers;
    }

    String getFirstCustomer() {
        return (customers.isEmpty()) ? null : customers.get(0).getName();
    }
}
