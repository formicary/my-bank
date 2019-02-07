package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void TestOpenAccount_OneAccount() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        Account account = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(sarah);
        sarah.openAccount(account);
        account.deposit(800);

        assertEquals(1, sarah.getNumberOfAccounts());
    }

    @Test
    public void TestOpenTwoAccounts() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        sarah.openAccount(new Account(1));
        sarah.openAccount(new Account(2));

        assertEquals(2, sarah.getNumberOfAccounts());
    }


    @Test
    public void TestOpenThreeAccounts() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        sarah.openAccount(new Account(1));
        sarah.openAccount(new Account(2));
        sarah.openAccount(new Account(3));

        assertEquals(3, sarah.getNumberOfAccounts());
    }

    @Test (expected=IllegalArgumentException.class)
    public void TestDeposit_NegativeAmount_ShouldThrowException() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        Account account = new Account(Account.SAVINGS);

        bank.addCustomer(bill);
        bill.openAccount(account);
        account.deposit(-300);
    }

    @Test
    public void TestDeposit_ValidAmount_ShouldUpdateBalanceCorrectly() {
        Bank bank = new Bank();
        Customer sarah = new Customer("Sarah");
        Account account = new Account(Account.MAXI_SAVINGS);

        bank.addCustomer(sarah);
        sarah.openAccount(account);
        account.deposit(800);

        assertEquals(800, account.getBalance());
    }

}