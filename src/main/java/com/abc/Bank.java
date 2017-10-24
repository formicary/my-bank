package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    
    private List<Customer> customers;

    protected Bank() {
        this.customers = new ArrayList<Customer>();
    }

    protected void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    private String format(int number) {
        String output;
        if (number==1) {
            output = String.format("%1$d %2$s",number, "account");
        } else {
            output = String.format("%1$d %2$s", number, "accounts");
        }
        return output;
    }
    
    protected String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            summary += String.format("%n - %1$s (%2$s)", c.getName(), format(c.getNumberOfAccounts()));
        }
        return summary;
    }

    protected double totalInterestPaid() {
        double total = 0.00;
        for(Customer c: customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

    protected String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "No customer found";
        }
    }
}
