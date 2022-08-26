package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer james = new Customer("James");

        bank.addCustomer(john);
        bank.addCustomer(james);

        john.openAccount(new Account(john, AccountType.CHECKING));
        james.openAccount(new Account(james, AccountType.CHECKING));
        james.openAccount( new Account(james, AccountType.SAVINGS));

        assertEquals("Customer Summary\n - John (1 account)\n - James (2 accounts)", bank.customerSummary());
    }

    // TODO Rewrite these tests
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = new Account(bill, AccountType.CHECKING);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = new Account(bill, AccountType.SAVINGS);
        bank.addCustomer(bill);
        bill.openAccount(checkingAccount);

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account checkingAccount = new Account(bill, AccountType.MAXI_SAVINGS);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
