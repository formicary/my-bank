package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append("\n - ");
            summary.append(c.getName());
            summary.append(" (");
            summary.append(format(c.getNumberOfAccounts(), "account"));
            summary.append(")");
        }
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    //Fractions of pennies are dealt with by rounding down the total value
    public double totalInterestPaid(LocalDate upToDate) {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned(upToDate);
        return Math.floor(total * 100.0) / 100.0;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
