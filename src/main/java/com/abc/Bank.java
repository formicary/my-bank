package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }


    public void addCustomer(Customer customer) {
        customers.add(customer);
    }


    /**
     * Generates customer summary, including the name of each customer and how many accounts they have
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + formatPlural(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }


    /**
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     */
    private String formatPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }


    /**
     * Calculate the total amount of interest paid over all customers in the bank
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

}
