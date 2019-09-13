package com.abc.Accounts;

import org.junit.Test;

public class AccountTest {

    @Test
    public void getTransactions() {
        Account account = new CheckingAccount();
        account.deposit(10);
        account.deposit(50);
        account.withdraw(10);
        account.withdraw(2);

    }

    @Test
    public void getName() {
    }

    @Test
    public void deposit() {
    }

    @Test
    public void withdraw() {
    }

    @Test
    public void interestEarned() {
    }

    @Test
    public void sumTransactions() {
    }
}