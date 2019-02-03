package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test // edited test names to reflect account being tested
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test // edited test names to reflect account being tested
    // edited interest to be paid based on edited method in Account class
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    // New test of Super Savings account

    @Test
    public void super_savings_account() {
        Bank bank = new Bank();
        Account superSavingsAccount = new Account(Account.SUPER_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(superSavingsAccount));

        superSavingsAccount.deposit(3000.0);

        assertEquals(3020.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    // test getFirstCustomer() method
    @Test
    public void get_first_customer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer bill = new Customer("Bill");
        Customer diane = new Customer("Diane");
        john.openAccount(new Account(Account.CHECKING));
        bill.openAccount(new Account(Account.MAXI_SAVINGS));
        diane.openAccount(new Account(Account.SUPER_SAVINGS));
        bank.addCustomer(john);
        bank.addCustomer(bill);
        bank.addCustomer(diane);

        assertEquals("John", bank.getFirstCustomer());
    }

    // test getFirstCustomer method for exception
    @Test
    public void get_first_customer_Exception() throws Exception {
        Bank bank = new Bank();

        bank.getFirstCustomer();
    }
}
