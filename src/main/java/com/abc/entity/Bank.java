package com.abc.entity;

import java.util.List;

/**
 * Bank interface for storing customers
 * @author aneesh
 */
public interface Bank {

    /**
     * Add customer to the bank
     * @param customer
     */
    void addCustomer(Customer customer);

    /**
     * obtain list of customers to the bank
     * @return list of all customers
     */
    List<Customer> getCustomers();
}
