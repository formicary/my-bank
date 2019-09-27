package com.abc;


import com.abc.users.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {

    Bank mockB;

    @BeforeEach
    public void init(){
        mockB = new Bank();
    }

    @Test
    public void testAddCustomer(){
        Customer customer = new Customer("mock");
        mockB.addCustomer(customer);

        assertTrue(mockB.getCustomers().contains(customer));
    }

    @Test
    public void testNumbOfCustomers(){
        mockB.addCustomer(new Customer("Bob"));
        mockB.addCustomer(new Customer("Steve"));
        mockB.addCustomer(new Customer("Neil"));

        assertEquals(3, mockB.getCustomers().size());
    }

}
