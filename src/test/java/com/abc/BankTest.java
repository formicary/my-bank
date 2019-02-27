package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryMultipleAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        john.openAccount(new Account(Account.SAVINGS));
        john.openAccount(new Account(Account.MAXI_SAVINGS));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (3 accounts)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryMultipleCustomers() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        Customer jane = new Customer("Jane");
        jane.openAccount(new Account(Account.SAVINGS));
        jane.openAccount(new Account(Account.MAXI_SAVINGS));
        bank.addCustomer(jane);

        assertEquals("Customer Summary\n - John (1 account)\n - Jane (2 accounts)", bank.customerSummary());
    }

}
