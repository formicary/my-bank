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

    // produces summary containing all customers in the bank
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + Formatters.format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    // totals up the interest paid across all customers and their accounts
    public double totalInterestPaid() {
        double total = 0.0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

}
