package com.abc;

import com.util.BigDecimalProvider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String getAllCustomersSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer customer : customers) {
            summary.append("\n - ");
            summary.append(customer.getName());
            summary.append(" (");
            int numberOfAccounts = customer.getNumberOfAccounts();
            summary.append(numberOfAccounts);
            summary.append(" ");
            summary.append(numberOfAccounts == 1 ? "account" : "accounts");
            summary.append(")");
        }
        return summary.toString();
    }

    public BigDecimal getTotalInterestsPaid() {
        BigDecimal total = BigDecimalProvider.getZero();
        for (Customer customer : customers)
            total = total.add(customer.totalInterestEarned());
        return total;
    }

    public boolean hasCustomer(Customer customer) {
        return customers.contains(customer);
    }
}
