package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryMultipleAccounts() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        john.openAccount(new SavingsAccount());
        john.openAccount(new MaxiSavingsAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (3 accounts)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryMultipleCustomers() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);
        Customer jane = new Customer("Jane");
        jane.openAccount(new SavingsAccount());
        jane.openAccount(new MaxiSavingsAccount());
        bank.addCustomer(jane);

        assertEquals("Customer Summary\n - John (1 account)\n - Jane (2 accounts)", bank.customerSummary());
    }

}
