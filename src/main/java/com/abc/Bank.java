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
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - " + c.getName() + " (" + Utils.format(c.getNumberOfAccounts(), "account") + ")");
        return summary.toString();
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        if (customers.isEmpty()) {
            return "Error";
        } else {
            return customers.get(0).getName();
        }
    }
}
