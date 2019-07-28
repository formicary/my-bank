package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {

    private static final double DOUBLE_DELTA = 1e-10;
    private Bank bank;
    private Customer customer;

    /**
     * Create new bank with one customer before each test
     */
    @Before
    public void init() {
        bank = new Bank();
        customer = new Customer("John");
        bank.addCustomer(customer);
    }


    /**
     * Check that customer summary prints correctly for single and plural account(s)
     */
    @Test
    public void customerSummary() {
        customer.openAccount(new Account(Account.CHECKING));
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());

        customer.openAccount(new Account(Account.CHECKING));
        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }


    /**
     * Make sure that total interest is calculated correctly when there are multiple customers
     */
    @Test
    public void totalInterestPaid() {

        // Adding another customer to bank
        Customer customer2 = new Customer("Bill");
        bank.addCustomer(customer2);

        // Add money to accounts for both customers
        Account checking = new Account(Account.CHECKING);
        checking.deposit(1000); // $1 interest
        customer.openAccount(checking);

        Account savings = new Account(Account.SAVINGS);
        savings.deposit(1000); // $1 interest
        customer2.openAccount(savings);

        assertEquals(2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
