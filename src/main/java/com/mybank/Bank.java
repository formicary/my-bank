package com.mybank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers;
    private List<String> customerNames;
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        try {
            customers.add(customer);
        } catch (Exception e) {
            logger.error("Error whilst adding customer", e);
        }
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - ")
                    .append(c.getName())
                    .append(" (").append(format(c.getNumberOfAccounts(), "account"))
                    .append(")");
        return summary.toString();
    }

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

    public List<String> getCustomers() {
        try {
            for (Customer customer : customers) {
                customerNames.add(customer.getName());
            }
            return customerNames;
        } catch (Exception e) {
            throw new RuntimeException("Error whilst getting customer", e);
        }
    }
}
