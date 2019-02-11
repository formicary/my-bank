package com.abc;

import java.util.ArrayList;

/**
 * A bank class that contains a list of customers.
 */
public class Bank {

    private ArrayList<Customer> customers;

    /**
     * Constructor for the Bank class, it sets up an array list to store customers.
     */
    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Method to create a new customer to add to the bank.
     *
     * @param name       The name of the new customer.
     * @param customerID A unique ID for that customer.
     * @throws IllegalArgumentException If a customer with that customer ID already exists.
     */

    public void newCustomer(String name, int customerID) throws IllegalArgumentException {

        try {
            getCustomer(customerID);
            throw new IllegalArgumentException("Customer already exists");
        } catch (IllegalArgumentException e) {
            Customer customer = new Customer(name, customerID);
            customers.add(customer);
        }
    }

    /**
     * Method to fetch a customer from the list of banks customers.
     *
     * @param customerID CustomerID of customer being fetched
     * @return THe customer
     * @throws IllegalArgumentException If the customer doesn't exist.
     */

    public Customer getCustomer(int customerID) throws IllegalArgumentException {

        for (Customer customer : customers) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        throw new IllegalArgumentException("Customer doesn't exist");
    }

    /**
     * Method to calculate the total interest paid by the bank
     *
     * @return the interest paid.
     */

    public double totalInterestPaid() {

        double total = 0;
        for (Customer customer : customers) {
            total += customer.totalInterestEarned();
        }

        return Math.round(total * 100.00) / 100.00;
    }

    /**
     * Method to view all the bank customers and number of accounts they have open.
     *
     * @return Summary of customers in String format.
     */
    public String customerSummary() {

        String summary = "Bank Customers:";

        for (Customer customer : customers)
            summary += "\n - " + customer.getCustomerID() + " " + customer.getName() + " [Number of accounts: " + customer.getNumberOfAccounts() + "]";
        return summary;
    }

    /**
     * toString method that returns all the information about all the customers.
     *
     * @return Customer list in string format
     */
    public String toString() {

        String toString = "Full customer information \n";

        for (Customer customer : customers) {
            toString += customer.toString();
        }

        return toString;
    }
}