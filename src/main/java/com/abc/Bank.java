package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    /**
     * Bank class constructor. Initialises the customers array list
     */
    public Bank() {
        this.customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer object to the array list of customers
     * @param customer Customer object
     */
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /**
     * Details the name and number of accounts of each customer in the 
     * customers array list
     * @return String Summary of each customer
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    /**
     * Make sure correct plural of word is created based on the number passed in 
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     * @param number Integer to be checked
     * @param word String to be adjusted
     * @return String Adjusted to be plural or singular
     */
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Calculates the total amount of interest paid by the bank for all each account
     * of each customer
     * @return double Total amount of interest paid
     */
    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

    /**
     * Returns the first name of the first item (customer) of the customers array list
     * @return String Name of the first customer
     */
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
