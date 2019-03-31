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

    /**
     * Provides a summary of every customer in the bank in the format:
     *      Customer Summary
     *      - CustomerName (number of accounts)
     */
    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");

        for (Customer c : customers){
            summary.append("\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")");
        }
        return summary.toString();
    }

    /**
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * returns the total amount of interest that the bank has paid to its customers
     */
    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

}
