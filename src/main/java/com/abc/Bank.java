package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summaryStringBuilder = new StringBuilder();
        summaryStringBuilder.append("Customer Summary");
        for (Customer customer : customers) {
            summaryStringBuilder.append("\n - ");
            summaryStringBuilder.append(customer.getName());
            summaryStringBuilder.append(" (");
            summaryStringBuilder.append(customer.getNumberOfAccounts());
            summaryStringBuilder.append(" ");
            summaryStringBuilder.append(customer.getNumberOfAccounts() == 1 ? "account" : "accounts");
            summaryStringBuilder.append(")");
        }
        return summaryStringBuilder.toString();
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer customer : customers) {
            total += customer.totalInterestEarned();
        }
        return total;
    }
}
