package com.abc;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Customer> customerMap;
    private double totalInterestPaid;

    Bank() {
        this.customerMap = new HashMap<>();
    }

    void addCustomer(Customer customer) {
        String customerKey = customer.getEmailAddress();
        if (customerMap.containsKey(customerKey)) {
            throw new IllegalArgumentException("Bank tried to register a new customer using an existing unique ID" +
                    " (email address): " + customerKey);
        } else {
            customerMap.put(customer.getEmailAddress(), customer);
        }
    }

    public Map getCustomerMap() { //todo do I need this?
        return this.customerMap;
    }

    String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer customer : customerMap.values()) {
            summary.append("\n - ").append(customer.getName()).append(" (")
                    .append(BankUtils.format(customer.getNumberOfAccounts(), "account")).append(")");
        }
        return summary.toString();
    }
    // Total interest paid is generated from transaction lists when called.
    // Performs more slowly than maintaining an 'interest paid' counter, but reduces potential for concurrency issues.
    double totalInterestPaid() {
        double total = 0; // Decimal?
        for (Customer customer : customerMap.values()) {
            total += customer.totalInterestEarned();
        }
        return total;
    }
}

