package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void testCheckingAccount() {
        Account a = new Account(Account.AccountType.CHECKING);

        assertEquals("CHECKING", a.getAccountType());
    }

    @Test
    public void testSavingsAccount() {
        Account a = new Account(Account.AccountType.SAVINGS);

        assertEquals("SAVINGS", a.getAccountType());
    }

    @Test
    public void testSuperSavingsAccount() {
        Account a = new Account(Account.AccountType.SUPER_SAVINGS);

        assertEquals("SUPER_SAVINGS", a.getAccountType());
    }

    @Test
    public void testMaxiSavingsAccount() {
        Account a = new Account(Account.AccountType.MAXI_SAVINGS);

        assertEquals("MAXI_SAVINGS", a.getAccountType());
    }
}