package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * A bank keeps a list of customers each with individual accounts,
 * allowing interest to be paid to the customer's accounts and reports
 * to be generated
 */
public class Bank {
    /**
     * A list of the bank's customers
     */
    private List<Customer> customers;

    /**
     * Constructor to initialise the list of customers
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a new customer object to the bank
     * @param customer the customer to be added
     */
    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer not provided");
        }

        customers.add(customer);
    }

    /**
     * Provides a string detailing the names of all customers in the bank, and the amount of accounts that each customer has
     * @return a string cointaining the report
     */
    public String customerSummary() {
        // create a stringbuilder and iterate over the customers list, adding a line for each customer
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers) {
            summary.append("\n - ")
                .append(c.getName())
                .append(" (")
                .append(format(c.getNumberOfAccounts(), "account"))
                .append(')');
        }
        return summary.toString();
    }

    /**
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     * @param number the quantity to base whether there are multiple off of
     * @param word the word to be made plural
     * @return the number and the formatted word
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Called to check each customer's accounts and credit them with interest if they're eligible
     */
    public void updateInterestPayments() {
        for (Customer c : customers) {
            c.updateInterestPayments();
        }
    }

    /**
     * Calculates the total amount of interest that the bank has paid to its customers
     * @return the total
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }
}
