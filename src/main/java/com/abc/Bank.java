package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(final Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append(String.format("\n - %s (%s)", c.getName(), StringUtils.quantityOfNoun(c.getNumberOfAccounts(), "account")));
        }
        return summary.toString();
    }



    public double totalInterestPaid() {
        return customers.stream().mapToDouble(Customer::totalInterestEarned).sum();
    }
}
