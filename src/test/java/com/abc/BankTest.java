package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.Type.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.Type.CHECKING);
        Account checkingAccount2 = new Account(Account.Type.CHECKING);

        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        bank.addCustomer( new Customer("Phil").openAccount(checkingAccount2));

        checkingAccount.deposit(100.0);
        checkingAccount2.deposit(100.0);

        assertEquals(0.2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingAccount = new Account(Account.Type.SAVINGS);
        Account savingAccount2 = new Account(Account.Type.SAVINGS);

        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));
        bank.addCustomer(new Customer("Phil").openAccount(savingAccount2));

        savingAccount.deposit(1500.0);
        savingAccount2.deposit(1500.0);

        assertEquals(4.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSaving = new Account(Account.Type.MAXI_SAVINGS);
        Account maxiSaving2 = new Account(Account.Type.MAXI_SAVINGS);

        bank.addCustomer(new Customer("Bill").openAccount(maxiSaving));
        bank.addCustomer(new Customer("Phil").openAccount(maxiSaving2));

        maxiSaving.deposit(3000.0);
        maxiSaving2.deposit(3000.0);

        assertEquals(340.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
}
