package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Bank category that represents a real bank with customers
 */
public class Bank {
    private List<Customer> customers;

    /**
     * Initialises a new bank object with an empty list of customers
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a new customer
     * @param customer customer to be added
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Creates a bank summary listing all customers and their accounts
     * @return summary of all customers and their accounts
     */
    public String generateCustomerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");

        for (Customer customer : customers) {
            summary.append("\n - ")
                    .append(customer.getCustomerName())
                    .append(" (")
                    .append(customer.getNumberOfAccounts())
                    .append(customer.getNumberOfAccounts() == 1 ? " account" : "  saccounts")
                    .append(")");
        }
        return summary.toString();
    }

    /**
     * Calculates the total interest paid on accounts to a given customer
     * @return totalled interest
     */
    public BigDecimal totalInterestPaid() {
        BigDecimal total = BigDecimal.ZERO;

        for (Customer customer : customers) {
            total = total.add(customer.totalInterestEarned());
        }
        return total;
    }
}
