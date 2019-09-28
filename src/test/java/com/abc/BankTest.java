package com.abc;


import com.abc.users.Customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@DisplayName("Testing Bank")
public class BankTest {

    Bank mockB;

    @BeforeEach
    public void init(){
        mockB = new Bank();
    }

    @Test
    @DisplayName("When a customer is added, bank should contain that customer")
    public void testAddCustomer(){
        Customer customer = new Customer("mock");
        mockB.addCustomer(customer);

        assertTrue(mockB.getCustomers().contains(customer));
    }

    @Test
    @DisplayName("When 3 customers are added, bank should contain 3 customers")
    public void testNumbOfCustomers(){
        mockB.addCustomer(new Customer("Bob"));
        mockB.addCustomer(new Customer("Steve"));
        mockB.addCustomer(new Customer("Neil"));

        assertEquals(3, mockB.getCustomers().size());
    }

}
