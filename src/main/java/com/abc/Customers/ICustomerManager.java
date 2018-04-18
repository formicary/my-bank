package com.abc.Customers;

/**
 * Represents an interface for a customer manager.
 */
public interface ICustomerManager {
    /**
     * Adds a new customer.
     *
     * @param name The name of the customer.
     *
     * @return The ID of the newly added customer.
     */
    int addCustomer(String name);

    /**
     * Gets the customer that corresponds to the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The customer.
     */
    ICustomer getCustomer(int customerId);

    /**
     * Gets the customer statement for the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The customer's bank statement.
     */
    String getCustomerStatement(int customerId);

    /**
     * Gets a summary of all known customers.
     *
     * @return The summary of all known customers.
     */
    String getCustomerSummary();

    /**
     * Calculates the total amount of interest paid.
     *
     * @return The total amount of interest paid.
     */
    double calculateTotalInterestPaid();
}
