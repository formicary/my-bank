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
        String summary = "Customer Summary\n";
        for (Customer customer : customers) {
        	int accounts = customer.getNumberOfAccounts();
        	String accountStr = (accounts == 1 ? "account" : "accounts");
            summary += String.format("\n%s: %d %s", customer.getName(), accounts, accountStr);
        }
        return summary;
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }
}
