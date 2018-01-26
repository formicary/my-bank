package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John", bank);
        john.openAccount(0);
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);
        Account checkingAccount = bill.openAccount(Account.CHECKING);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill", bank);
        bill.openAccount(Account.SAVINGS);
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(bill);

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();

        Customer bill = new Customer("Bill", bank);
        Account checkingAccount = bill.openAccount(Account.MAXI_SAVINGS);
        bank.addCustomer(bill);

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
