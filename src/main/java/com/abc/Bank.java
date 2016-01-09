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
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + Utils.format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    // Note: this function is not used anywhere else in this application
    public String getFirstCustomer() {
        if (customers.isEmpty()) {
            return "Error";
        } else {
            return customers.get(0).getName();
        }
    }
}
