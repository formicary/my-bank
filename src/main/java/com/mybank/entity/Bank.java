package com.mybank.entity;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
