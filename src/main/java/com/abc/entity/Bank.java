package com.abc.entity;

import com.abc.util.InterestCalculator;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }


    public List<Customer> getCustomers() {
        return customers;
    }
}
