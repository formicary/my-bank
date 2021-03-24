package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test // test checking account
    public void testCheckingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), 0);
    }

    @Test // test savings account
    public void testSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), 0);
    }

    @Test // test maxi savings account
    public void testMaxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), 0);
    }

    @Test // test sum transactions
    public void testSumTransactions() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        checkingAccount.deposit(1000.0);
        checkingAccount.withdraw(100.0);
        checkingAccount.withdraw(50.0);
        checkingAccount.deposit(100.0);

        assertEquals(1050, checkingAccount.sumTransactions(), 0);
    }

    @Test // test account type
    public void testGetAccountType() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);

        assertEquals(1, savingsAccount.getAccountType(), 0);
    }

    @Test // test transfer
    public void testTransfer() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(savingsAccount).openAccount(checkingAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(800.0);
        savingsAccount.transfer(500.0, checkingAccount);

        assertEquals(300.0, savingsAccount.sumTransactions(), 0);
        assertEquals(500.0, checkingAccount.sumTransactions(), 0);
    }

}
