package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Bank class
 */
public class BankTest {

    /**
     * Tests the creation of a summary for a single account
     */
    @Test
    public void customerSummarySingleAccount() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING, "checking"));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    /**
     * Tests the creation of a summary for a two accounts
     */
    @Test
    public void customerSummaryTwoAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING, "checking"));
        bank.addCustomer(john);

        Account savingsAccount = new Account(Account.SAVINGS, "savings");
        john.openAccount(savingsAccount);

        assertEquals("Customer Summary\n - John (2 account's)", bank.customerSummary());
    }
}
