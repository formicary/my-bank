package com.abc.Customers;

/**
 * Exposes methods to manage unique IDs for customers in bank
 */
public interface ICustomerIdManager {
    /**
     * Generates the customer ID.
     *
     * @return The customer ID.
     */
    int generateCustomerId();
}
