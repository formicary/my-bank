package com.abc.entity;

import com.abc.entity.impl.CustomerImpl;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<CustomerImpl> customers;

    public Bank() {
        customers = new ArrayList<CustomerImpl>();
    }

    public void addCustomer(CustomerImpl customer) {
        customers.add(customer);
    }


    public List<CustomerImpl> getCustomers() {
        return customers;
    }
}
