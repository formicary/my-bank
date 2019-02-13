package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    @Test
    public void testCheckingAccount(){
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));

        assertEquals(1, john.getNumberOfAccounts());
    }

    @Test
    public void testSavingsAccount(){
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.SAVINGS));

        assertEquals(1, john.getNumberOfAccounts());
    }

    @Test
    public void testMaxiSavingsAccount(){
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.MAXI_SAVINGS));

        assertEquals(1, john.getNumberOfAccounts());
    }
}
