package com.abc;

import org.junit.Test;

import com.abc.Account.Type;

import static org.junit.Assert.assertEquals;

/**
 * Tester for the methods of the Bank class.
 * @author Filippos Zofakis
 */
public class BankTest {
    @Test // Test customer summary generation.
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", "Smith");
        john.openAccount(new Account(Type.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary:\n - John Smith (1 account)", bank.customerSummary());
        System.out.println("Summary of all customer accounts at the bank is generated correctly.");
    }
}
