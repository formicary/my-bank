package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");

        for (Customer customer : customers) {
            summary.append("\n - " + customer.getName());
            summary.append(" (" + formatPlural(customer.getNumberOfAccounts(), "account") + ")");
        }
        return summary.toString();
    }

    private String formatPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer customer : customers) {
            total += customer.totalInterestEarned();
        }

        return total;
    }

    public String getFirstCustomer() {
        if (customers.size() > 0) {
            return customers.get(0).getName();
        }
        return null;
    }
}
