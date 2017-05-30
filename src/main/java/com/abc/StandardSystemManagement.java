package com.abc;

import com.google.common.collect.Maps;

import java.util.Map;

public class StandardSystemManagement implements SystemManagement {
    private static Map<Integer, Customer> customers;

    public StandardSystemManagement() {
        customers = Maps.newConcurrentMap();
    }

    public void addCustomer(Customer customer) {
        customers.put(customers.size() + 1, customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers.values())
            summary += "\n - " + c.getName() + " (" + format(c.getAccountManagement().getNumberOfAccounts(), "account") + ")";
        return summary;
    }


    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers.values())
            total += c.getAccountManagement().totalInterestEarned();
        return total;
    }

    public Map<Integer, Customer> getCustomers() {
        return customers;
    }

    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
