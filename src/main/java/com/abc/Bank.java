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
        if(!customers.isEmpty()) {
            for (Customer c : customers)
                summary += "\n - " + c.getName() + " (" + Format.multiples(c.getNumberOfAccounts(), "account") + ")";
        }
        else
        {
            summary += "\n Bank has no customers";
        }
        return summary;
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }


}
