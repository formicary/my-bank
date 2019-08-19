package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    protected List<Customer> customers = new ArrayList<Customer>();

    public Bank() {
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");

        for (Customer customer : customers)
            summary.append("\n - ").append(customer.getName()).append(" (").append(handlePluralAccount(customer.getNumberOfAccounts())).append(")");

        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String handlePluralAccount(int number) {
        return number + " " + (number == 1 ? "account" : "accounts");
    }

    public double totalInterestPaid() {
        double total = 0;

        for(Customer customer : customers)
            total += customer.totalInterestEarned();

        return total;
    }
}
