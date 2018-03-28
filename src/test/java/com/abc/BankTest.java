package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCustomerSummarySingleAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void testCustomerSummaryTwoAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John");
        john.openAccount(checkingAccount);
        john.openAccount(savingAccount);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }

    @Test
    public void testCheckingAccountInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(1), DOUBLE_DELTA);
    }

    @Test
    public void testSavingsAccountInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(1), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiSavingsAccountInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(176.7, bank.totalInterestPaid(1), DOUBLE_DELTA);
    }
}
