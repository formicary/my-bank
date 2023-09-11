package com.abc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers;
    private static Logger logger = LoggerFactory.getLogger(Bank.class);

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
            summary.append("\n - ").append(c.getName()).append(" (").append(format(c.getNumberOfAccounts(), "account")).append(")");
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e) {
            throw new RuntimeException("Error whilst getting customer", e);
        }
    }
}
