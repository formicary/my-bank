package com.abc.Customers;

/**
 * Exposes methods to manage unique IDs for customers in a bank.
 */
public class CustomerIdManager implements ICustomerIdManager {
    /**
     * The customer ID manager instance.
     */
    private static final CustomerIdManager instance = new CustomerIdManager();

    /**
     * The latest customer ID.
     */
    private int latestCustomerId;

    /**
     * Initializes a new instance of the CustomerIdManager class.
     */
    private CustomerIdManager() {
        this.latestCustomerId = 0;
    }

    /**
     * Returns the single instance of this CustomerIdManager class.
     *
     * @return The single instance of this CustomerIdManager class.
     */
    public static CustomerIdManager getInstance() {
        return instance;
    }

    /**
     * Generates the customer ID.
     *
     * @return The customer ID.
     */
    public int generateCustomerId() {
        this.latestCustomerId++;

        return this.latestCustomerId;
    }
}
