package com.abc;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    @Test
    public void test_checkingAccountRate() {
        Account a = new CheckingAccount();
        assertEquals(0.1, a.getRateByDate(LocalDateTime.now()),0);
    }

    @Test
    public void test_savingAccountRateDeposit1000() {
        Account a = new SavingsAccount();
        a.deposit(1000);
        assertEquals(0.1, a.getRateByDate(LocalDateTime.now()),0);
    }

    @Test
    public void test_savingAccountRateDeposit1001() {
        Account a = new SavingsAccount();
        a.deposit(1001, LocalDateTime.now().minusDays(1));
        assertEquals(0.2, a.getRateByDate(LocalDateTime.now()),0);
    }

    @Test
    public void test_savingAccountRateNoWithdraw() {
        Account a = new MaxiSavingsAccount();
        assertEquals(5, a.getRateByDate(LocalDateTime.now()),0);
    }

    @Test
    public void test_savingAccountRateWithdrawInCheckedPeriod() {
        Account a = new MaxiSavingsAccount();
        a.withdraw(100);
        assertEquals(0.1, a.getRateByDate(LocalDateTime.now()),0);
    }

    @Test
    public void test_savingAccountRateWithdrawNotInCheckedPeriod() {
        Account a = new MaxiSavingsAccount();
        a.withdraw(100);
        Optional<Transaction> t = a.getTransactions().stream().findAny();
        assertTrue(t.isPresent());
        t.get().setTransactionDate(LocalDateTime.now().minusDays(11));
        assertEquals(5, a.getRateByDate(LocalDateTime.now()),0);
    }
}
