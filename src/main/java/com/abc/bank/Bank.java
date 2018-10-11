package com.abc.bank;

import com.abc.customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
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

    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
