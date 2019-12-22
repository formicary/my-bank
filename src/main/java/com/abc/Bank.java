package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    //Summary of all the customers
    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - ").append(c.getName()).append(" (").append(Utils.format(c.getNumberOfAccounts(), "account")).append(")");
        return summary.toString();
    }

    //The total compund interest the bank has to pay to the given date
    public double totalInterestPaid(Date date) {
        double total = 0;
        for(Customer c: customers)
            total += c.totalCompoundInterestEarned(date);
        return total;
    }

    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (IndexOutOfBoundsException e){
            System.out.println("No customer found!");
            return "No customer found!";
        }
    }
}
