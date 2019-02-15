package com.abc;

import java.util.ArrayList;

public class Bank {

    //Fields to represent a bank
    private ArrayList<Customer> customers;

    /**
     * Creates a new instance of a bank
     *
     */
    public Bank() {
        customers = new ArrayList<>();
    }

    /**
     *
     * @param customer a customer to be added under this bank
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     *
     * @return A summary of a customer, i.e. their name and the number of
     * account they own
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            summary += "\n - " + c.getCustomerID() + ": " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     *
     * @return The total amount of interest paid to all customer by the bank
     */
    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

    /**
     *
     * @return The first customer's name in the bank if exists
     */
    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e) {
            return "Error: " + e;
        }
    }

}
