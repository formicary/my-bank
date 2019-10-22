package com.abc;

import java.util.ArrayList;
import java.util.List;

class Bank {
    private List<Customer> customers;

    Bank() {
        customers = new ArrayList<>();
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }

    String getCustomerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append("\n - ").append(c.getName()).append(" (").append(format(c.getNumberOfAccounts())).append(")");
        }
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number) {
        return number + " " + (number == 1 ? "account" : "account" + "s");
    }

    double getTotalInterestPaid() {
        double total = 0;
        for (Customer c : customers) {
            total += c.getTotalInterestEarned();
        }
        return total;
    }

    String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
