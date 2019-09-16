package com.abc;

import com.abc.Customer.Customer;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a reference to all of the banks customers
 */
public class Bank {
    private ArrayList<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Add new customer to list of customers for bank
     * @param customer
     * @return
     */
    public Customer addCustomer (Customer customer) {
        customers.add(customer);
        return customer;
    }

    public ArrayList<Customer> getCustomers(){
        return customers;
    }
}
