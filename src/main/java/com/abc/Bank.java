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

    public String getCustomerSummary(Customer customer) {
        if (customers.contains(customer)) {
            String summary = "Customer Summary";
            summary += "\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")";
            return summary;
        } else {
            throw new IllegalArgumentException("couldn't find this customer");
        }
    }

    public String getAllCustomersSummary() {
        String summary = "Customer Summary";
        for (Customer customer : customers)
            summary += "\n - " + customer.getName() + " (" + format(customer.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    // isShowForTheWHoleYear is used when the Customer wants to see the Interest for the whole year upfront
    public double totalInterestPaid(Boolean isShowForTheWHoleYear) {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned(isShowForTheWHoleYear);
        return total;
    }

    public Customer getFirstCustomer() {
        if (customers.size() > 0) {
            return customers.get(0);
        } else {
            throw new NullPointerException("no customers in the bank");
        }

    }
}
