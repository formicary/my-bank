package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void oneCustomerWithZeroAccountsSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (0 accounts)", bank.customerSummary());
    }

    @Test
    public void oneCustomerWithOneAccountSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void oneCustomerWithTwoAccountsSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        assertEquals("Customer Summary\n - Alice (2 accounts)", bank.customerSummary());
    }

    @Test
    public void twoCustomersWithOneAccountEachSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Alice (1 account)\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void twoCustomersWithOneAccountAndTwoAccountsSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Alice (1 account)\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void twoCustomersWithTwoAccountsEachSummary() {
        Bank bank = new Bank();
        Customer alice = new Customer("Alice");
        alice.openAccount(new Account(Account.CHECKING));
        alice.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(alice);

        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - Alice (2 accounts)\n - John (2 accounts)", bank.customerSummary());
    }
}
