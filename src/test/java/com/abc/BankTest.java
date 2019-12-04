package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(Account.AccountType.CHECKING);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);

        Account checkingAccount = bill.openAccount(Account.AccountType.CHECKING);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);

        Account savingsAccount = bill.openAccount(Account.AccountType.SAVINGS);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);

        Account maxiAccount = bill.openAccount(Account.AccountType.MAXI_SAVINGS);

        maxiAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
