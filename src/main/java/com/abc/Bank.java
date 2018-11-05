/**
 * Class represents a bank with all of its customers and interest paid.
 */


package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Customer> customers;


    /**
     * Create a bank and initialise an empty list of customers.
     */

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Add a customer to the bank.
     * @param customer The customer to add.
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * This is a summary of all customers and their name, number of accounts etc.
     * @return The customer summary.
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     */

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculate the total interest paid to all customers.
     * @return The total interest paid.
     */

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Get the first customer whom has joined the Bank.
     * @return The first customer to join or null if no customers.
     */

    public String getFirstCustomer() {

        if(customers != null) {
            return customers.get(0).getName();     // POSSIBLE THROW EXCEPTION????
        }
        else {
            return "Error, there are no customers at this Bank!";
        }
    }
}
