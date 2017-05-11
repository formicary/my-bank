package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankTest {
    @Test
    public void addCustomer() throws Exception {
        Bank bank = new Bank();
        Customer jack = new Customer("Jack");
        jack.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(jack);
        assertTrue(bank.getFirstCustomer().equals("Jack"));
    }

    @Test
    public void totalInterestPaid() throws Exception {
        Bank bank = new Bank();
        Customer jack = new Customer("Jack");
        Account jacksCheckingAcc = new Account(AccountType.CHECKING);
        jack.openAccount(jacksCheckingAcc);
        bank.addCustomer(jack);
        jack.deposit(jacksCheckingAcc, 300.0);
        assertTrue(bank.totalInterestPaid() == 0.3);

        Account jacksSavingsAcc = new Account(AccountType.SAVINGS);
        jack.openAccount(jacksSavingsAcc);
        jack.deposit(jacksSavingsAcc, 1001);
        assertTrue(bank.totalInterestPaid() == 1.302);

    }

    @Test
    public void getFirstCustomer() throws Exception {
        addCustomer();
    }

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummaries() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        Customer babyGroot = new Customer("Baby Groot");
        babyGroot.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(babyGroot);
        assertEquals("Customer Summaries\n - John (1 account)\n - Baby Groot (1 account)", bank.customerSummaries());
    }

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        Customer babyGroot = new Customer("Baby Groot");
        babyGroot.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(babyGroot);
        assertEquals("Customer Summary\n - Baby Groot (1 account)", bank.customerSummary(babyGroot));
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

}
