package com.abc.Customers;

/**
 * Represents an interface for a factory for creating customers.
 */
public interface ICustomerFactory {
    /**
     * Creates a customer.
     *
     * @param name The name.
     * @param customerId The customer ID.
     *
     * @return The newly created customer.
     */
    ICustomer createCustomer(String name, int customerId);
}
