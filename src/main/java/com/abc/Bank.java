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
        StringBuilder sb = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            sb.append(String.format("\n - %s (%s)", c.getName(), FormatUtils.toPlural(c.getNumberOfAccounts(), "account")));
        return sb.toString();
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

    public Customer getFirstCustomer() {
        try {
            return customers.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
