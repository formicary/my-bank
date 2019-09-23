package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummaryPrintsCorrectly() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccountInterestIsCorrect() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        Account checkingAccount = new Account(AccountType.CHECKING);
        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccountInterestIsCorrect() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.SAVINGS));
        Account checkingAccount = new Account(AccountType.SAVINGS);
        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestIsCorrect() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.SAVINGS));
        Account checkingAccount = new Account(AccountType.SAVINGS);
        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void nameOfFirstCustomerIsCorrect() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Customer bill = new Customer("Bill");

        assertEquals(bank.getFirstCustomer(), "John", DOUBLE_DELTA);
    }

    @Test
    public void noCustomerIsPrintedIfThereAreNoCustomers() {
        Bank bank = new Bank();

        assertEquals(bank.getFirstCustomer(), "No customers.", DOUBLE_DELTA);
    }

}
