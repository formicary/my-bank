package com.abc.Customers;

import com.abc.Accounts.IAccountManager;

/**
 * Represents a factory for creating customers.
 */
public class CustomerFactory implements ICustomerFactory {
    /**
     * The account manager.
     */
    private IAccountManager accountManager;

    /**
     * Initializes a new instance of the CustomerFactory
     *
     * @param accountManager The account manager.
     */
    public CustomerFactory(IAccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * Creates a customer.
     *
     * @param name The name.
     * @param customerId The customer ID.
     *
     * @return The newly created customer.
     */
    public ICustomer createCustomer(String name, int customerId) {
        return new Customer(this.accountManager, name, customerId);
    }
}
