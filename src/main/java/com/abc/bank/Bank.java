package com.abc.bank;

import com.abc.customer.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a List of Customers, allowing for summaries to be generated of them
 */
public class Bank {

    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) summary = summary.concat(String.format("\n - %s", c));
        System.out.println(summary);
        return summary;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
}
