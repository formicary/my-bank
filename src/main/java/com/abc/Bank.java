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
        for (Customer c : customers) {
            summary += "\n - " + c.getCustomerName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers) {
            total += c.getTotalInterestEarned();
        }
        return total;
    }

    public String getFirstCustomer() {
        String customerName = "";
        if (!customers.isEmpty()) {
            if (customers.size() > 0) {
                customerName = customers.get(0).getCustomerName();
            }
        } else {
            return "No Customer Found. Please Try Again";
        }
        return customerName;
    }
}
