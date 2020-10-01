package com.abc.entity;

import java.util.List;

/**
 * Bank interface for storing customers
 * @author aneesh
 */
public interface Bank {

    void addCustomer(Customer customer);
    List<Customer> getCustomers();
}
