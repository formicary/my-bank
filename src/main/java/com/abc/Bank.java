package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        if (customer != null && !customers.contains(customer)) {
            customers.add(customer);
        }
    }

    public String createCustomerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer customer : customers) {
            summary.append("\n - ");
            summary.append(customer.getName());
            summary.append(" (");
            summary.append(customer.getNumberOfAccounts());
            summary.append(pluralFormat(customer.getNumberOfAccounts(), " account"));
            summary.append(")");
        }
        return summary.toString();
    }

    /**
     * @return If number passed in is 1 return the word otherwise add an 's' at the end
     */
    private String pluralFormat(int number, String word) {
        return number == 1 ? word : word + "s";
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer customer : customers)
            total += customer.totalInterestEarned();
        return total;
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }
}
