package com.abc.entity;

import java.util.List;

public interface Bank {

    void addCustomer(Customer customer);
    List<Customer> getCustomers();
}
