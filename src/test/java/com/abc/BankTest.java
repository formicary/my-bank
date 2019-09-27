package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);
        john.openAccount(Account.CHECKING);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void customerSummaryNoAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryNoCustomers() {
        Bank bank = new Bank();
        assertEquals("Customer Summary", bank.customerSummary());
    }

    @Test
    public void customerSummaryMultipleAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);
        john.openAccount(Account.CHECKING);
        john.openAccount(Account.SAVINGS);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void customerSummaryMultipleCustomers() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);
        Customer harambe = new Customer("Harambe", bank);
        harambe.openAccount(Account.CHECKING);
        john.openAccount(Account.CHECKING);

        assertEquals("Customer Summary\n" +
                " - John (1 account)\n" +
                " - Harambe (1 account)", bank.customerSummary());
    }

    @Test
    public void totalInterestPaid() {
        Bank bank = new Bank();
        Customer george = new Customer("George", bank);
        Customer bob = new Customer("Bob", bank);
        Customer alice = new Customer("Alice", bank);

        george.openAccount(Account.CHECKING);
        bob.openAccount(Account.CHECKING);
        alice.openAccount(Account.CHECKING);

        george.deposit(1000, 0);
        bob.deposit(1000, 0);
        alice.deposit(1000, 0);

        assertEquals(3, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
