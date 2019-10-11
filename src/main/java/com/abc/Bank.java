package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * TASK
 * A bank manager can get a report showing the list of customers and how many accounts they have
 * A bank manager can get a report showing the total interest paid by the bank on all accounts
 */

/**
 * The Bank class holds all the information regarding its Customers.
 */
public class Bank {
    private List<Customer> customers;

    /**
     * Initializes a new Bank with no Customers.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a new Customer to the Bank.
     *
     * @param customer new customer object
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * List all customers and the amount of accounts they have opened.
     *
     * @return list of customer information
     */
    public String customerSummary() {
        // TODO: 10/10/2019 Use a StringBuilder here
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Total interest paid to all customers.
     *
     * @return total interest paid out
     */
    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

    /**
     * Broken method.
     *
     * @return error
     */
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
