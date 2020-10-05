package com.abc.entity.impl;

import com.abc.entity.Bank;
import com.abc.entity.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code Bank}
 * @author aneesh
 */
public class BankImpl implements Bank {

    private List<Customer> customers;

    public BankImpl() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
