package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(0.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void interestTest_Checking_One() {
        Account a = new Account(Account.AccountType.CHECKING);

        assertEquals(1.0, a.checkingInterest(1000.0, 365), DOUBLE_DELTA);
    }

    @Test
    public void interestTest_Checking_Two() {
        Account a = new Account(Account.AccountType.CHECKING);

        assertEquals(10.0, a.checkingInterest(365000.0, 10), DOUBLE_DELTA);
    }

    @Test
    public void interestTest_Savings() {
        Account a = new Account(Account.AccountType.SAVINGS);

        assertEquals(3.0, a.savingsInterest(2000.0, 365), DOUBLE_DELTA);
    }

    @Test
    public void interestTest_MaxiSavings_One() {
        Account a = new Account(Account.AccountType.MAXI_SAVINGS);

        assertEquals(18250.0, a.maxisavingsInterest(365000.0, 365), DOUBLE_DELTA);
    }

    @Test
    public void interestTest_MaxiSavings_Two() {
        Account a = new Account(Account.AccountType.MAXI_SAVINGS);

        assertEquals(5.0, a.maxisavingsInterest(365000.0, 5), DOUBLE_DELTA);
    }

}
