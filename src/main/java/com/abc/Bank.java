package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public Bank addCustomer(Customer customer) {
        customers.add(customer);
        return this;
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer cus : customers) {
            summary += "\n - " + cus.getSummary();
        }
        return summary;
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer cus: customers) {
            total += cus.totalInterestEarned();
        }
        return total;
    }

}
