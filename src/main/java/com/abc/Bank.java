package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers; // List of customers

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        // Returns customer's info and his/her number of accounts
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - ").append(c.getName()).append(" (")
                    .append(format(c.getNumberOfAccounts())).append(")");
        return summary.toString();
    }

    private String format(int number) {
        // Returns the singular or plural form of accounts based on the number of accounts
        return number + " " + (number == 1 ? "account" : "accounts");
    }

    public double totalInterestPaid() {
        return totalInterestPaid(LocalDate.now());
    }

    public double totalInterestPaid(LocalDate date) {
        // Returns total amount of interests paid by the bank. Date to be used only for testing purposes.
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned(date);
        return total;
    }
}
