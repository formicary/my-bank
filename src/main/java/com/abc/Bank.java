package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        this.customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public String customerSummaries() {
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summaries");
        for (Customer c : customers)
            summary.append("\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")");
        return summary.toString();
    }

    public String customerSummary(Customer c) {
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");
        if (this.customers.contains(c)) {
            summary.append("\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")");
        }
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
