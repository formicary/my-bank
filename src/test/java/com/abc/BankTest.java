package com.abc;

import com.abc.customer.Customer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {

    @Test
    public void addCustomer(){
        Customer fred = new Customer("fred");
        Bank bank = new Bank();
        bank.addCustomer(fred);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        customers.add(fred);
        assertEquals(customers, bank.getCustomers());
    }

}
