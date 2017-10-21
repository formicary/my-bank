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
        double expected = (100.0 * Account.CHECKING_INTEREST_RATE) / Account.daysPerAnnum;
        assertEquals(expected, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);
        double expected = (1000.0 * Account.SAVINGS_INITIAL_INTEREST_RATE) / Account.daysPerAnnum + (500.0*Account.SAVINGS_FINAL_INTEREST_RATE)/Account.daysPerAnnum;
        assertEquals(expected, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(4500.0);
        double expected = (4500 * Account.MAXI_SAVINGS_INITIAL_INTEREST_RATE / Account.daysPerAnnum);
        assertEquals(expected, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
