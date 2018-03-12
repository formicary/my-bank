package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/* Written by Tunc Demircan */
public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testAddCustomer(){
        Bank b = new Bank();
        Customer c = new Customer("c");

        b.addCustomer(c);
        assertTrue(b.getCustomers().contains(c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCustomerFail(){
        Bank b = new Bank();
        Customer c = new Customer("c");

        b.addCustomer(c);
        b.addCustomer(c);
    }

    @Test
    public void testOpenAccount(){
        Bank b = new Bank();
        Customer c = new Customer("c");
        Account a = new Account(0);

        b.addCustomer(c);

        b.openAccount(c, a);
        assertTrue(c.getAccounts().contains(a));
        assertEquals(1, c.getNumberOfAccounts());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenAccountFail(){
        Bank b = new Bank();
        Customer c = new Customer("c");

        b.openAccount(c, new Account(0));
    }

    @Test
    public void testCustomerSummary(){
        Bank b = new Bank();
        Customer c = new Customer("c");
        Account a = new Account(0);

        b.addCustomer(c);
        b.openAccount(c, a);
        assertEquals("Customer Summary\nc (1 account)", b.customerSummary());

        b.openAccount(c, a);
        assertEquals("Customer Summary\nc (2 accounts)", b.customerSummary());

    }

    @Test
    public void testTotalInterestPaid(){
        Bank b = new Bank();
        Customer c = new Customer("c");
        Account a = new Account(0);

        b.addCustomer(c);
        b.openAccount(c, a);

        a.deposit(10);
        a.payInterest(1);

        assertEquals(0.01, b.totalInterestPaid(), DOUBLE_DELTA);

        a.payInterest(1);
        assertEquals(0.02001, b.totalInterestPaid(), DOUBLE_DELTA);

    }
}
