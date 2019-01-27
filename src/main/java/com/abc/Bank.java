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

    public String getCustomerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double getTotalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.getTotalInterestEarned();
        return total;
    }
}
