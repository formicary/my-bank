package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that handles bank level functions
 */
public class Bank {
    private List<Customer> customers; // A list containing all customer of bank

    /**
     * Constructor for the bank class
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Functions to add customers to the bank
     * @param customer the customer to be added to bank
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * Function that provides a summary of all customers
     * @return a summary of all customers
     */
    public String customerSummary() {
        StringBuilder stringBuilder = new StringBuilder("Customer Summary");
        for (Customer customer : customers) {
            stringBuilder.append("\n - ");
            stringBuilder.append(customer.getName());
            stringBuilder.append(" (");
            stringBuilder.append(formatWordPlural(customer.getNumberOfAccounts(), "account"));
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    /**
     * Function that provides correct plural of word. If number passed in is 1 just return the word otherwise add an 's' at the end
     * @param number number of items
     * @param word the word to format
     * @return formatted word
     */
    private String formatWordPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "'s");
    }

    /**
     * Function that calculates total amount of interest earned by all customers
     * @return total interest earned by customers
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer customer: customers)
            total += customer.totalInterestEarned();
        return total;
    }
}
