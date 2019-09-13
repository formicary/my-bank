package com.abc.Account;

import com.abc.Transaction;
import org.junit.Test;

import static org.junit.Assert.fail;

public class AccountTest {

    @Test
    public void getTransactions() {
        Account account = new CheckingAccount();
        try{
            account.processTransaction(new Transaction(15));
            account.processTransaction(new Transaction(15));
            account.processTransaction(new Transaction(-10));
            account.processTransaction(new Transaction(-2));
        } catch(Exception e) {
            fail("Exception thrown unexpectedly");
        }
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