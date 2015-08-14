package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void testCheckingAccount() {
        Account a = new Account(Account.AccountType.CHECKING);

        assertEquals("Checking Account", a.getAccountType().toString());
    }

    @Test
    public void testSavingsAccount() {
        Account a = new Account(Account.AccountType.SAVINGS);

        assertEquals("Savings Account", a.getAccountType().toString());
    }

    @Test
    public void testSuperSavingsAccount() {
        Account a = new Account(Account.AccountType.SUPER_SAVINGS);

        assertEquals("Super-Savings Account", a.getAccountType().toString());
    }

    @Test
    public void testMaxiSavingsAccount() {
        Account a = new Account(Account.AccountType.MAXI_SAVINGS);

        assertEquals("Maxi-Savings Account", a.getAccountType().toString());
    }
}