package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void testGetStatement(){
        Customer tunc = new Customer("Tunc");
        Bank bank = new Bank();

        assertEquals("Statement for Tunc\nNo accounts to display.",
                     tunc.getStatement());

        bank.addCustomer(tunc);
        bank.openAccount(tunc, new Account(0));

        assertEquals("Statement for Tunc\n\n" + "Checking Account\nBalance: $0.00\n" +
                    "Transactions:\nNo transactions to display\n\n",tunc.getStatement());

        tunc.getAccounts().get(0).deposit(1.00);
        assertEquals("Statement for Tunc\n\n" + "Checking Account\nBalance: $1.00\n" +
                "Transactions:\n$1.00 deposit\n\n",tunc.getStatement());
    }

    @Test
    public void testIncrement(){
        Customer c = new Customer("c");
        assertEquals(0, c.getNumberOfAccounts());

        c.incrementNumberOfAccounts();
        assertEquals(1, c.getNumberOfAccounts());
    }
}