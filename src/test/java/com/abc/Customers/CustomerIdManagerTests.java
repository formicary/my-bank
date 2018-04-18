package com.abc.Customers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents tests for the CustomerIdManager class.
 */
public class CustomerIdManagerTests {
    /**
     * The customer ID manager.
     */
    private ICustomerIdManager customerIdManager;

    @Before
    public void setUp() {
        this.customerIdManager = CustomerIdManager.getInstance();
    }

    /**
     * Tests that a customer ID is generated correctly.
     */
    @Test
    public void generatesCustomerIdCorrectly() {
        int customerId = this.customerIdManager.generateCustomerId();

        Assert.assertNotEquals(customerId, 0);
    }

    /**
     * Tests that different customer IDs are generated each time.
     */
    @Test
    public void generatesUniqueCustomerIds() {
        int firstCustomerId = this.customerIdManager.generateCustomerId();
        int secondCustomerId = this.customerIdManager.generateCustomerId();

        Assert.assertNotEquals(firstCustomerId, secondCustomerId);
    }
}
