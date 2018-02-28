package com.abc;

import org.junit.Test;

import java.util.List;

import static com.abc.AccountType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccountTest extends TestBase {
    @Test
    public void shouldEmptyCreateSavingsAccount() {
        Account acc = new Account(SAVINGS);
        assertEquals(acc.getAccountType(), SAVINGS);
        assertEquals(acc.getTransactions().size(), 0);
    }

    @Test
    public void shouldEmptyCreateMaxiSavingsAccount() {
        Account acc = new Account(MAXI_SAVINGS);
        assertEquals(acc.getAccountType(), MAXI_SAVINGS);
        assertEquals(acc.getTransactions().size(), 0);
    }

    @Test
    public void shouldEmptyCreateCheckingAccount() {
        Account acc = new Account(CHECKING);
        assertEquals(acc.getAccountType(), CHECKING);
        assertEquals(acc.getTransactions().size(), 0);
    }

    @Test
    public void shouldDepositPositiveAmountsToAccount() {
        Account acc = new Account(CHECKING);
        acc.deposit(1);
        acc.deposit(2);
        acc.deposit(3);
        List<Transaction> transactions = acc.getTransactions();
        assertEquals(transactions.size(), 3);
        assertEquals(transactions.get(0).amount, 1, DOUBLE_DELTA);
        assertEquals(transactions.get(1).amount, 2, DOUBLE_DELTA);
        assertEquals(transactions.get(2).amount, 3, DOUBLE_DELTA);
    }

    @Test
    public void shouldNotAllowToDepositNonPositiveValues() {
        Account acc = new Account(CHECKING);
        try {
            acc.deposit(0);
            fail("Depositing 0 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            acc.deposit(-1);
            fail("Depositing -1 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void shouldWithdrawPositiveAmountsFromAccount() {
        Account acc = new Account(CHECKING);
        acc.withdraw(1);
        acc.withdraw(2);
        acc.withdraw(3);
        List<Transaction> transactions = acc.getTransactions();
        assertEquals(transactions.size(), 3);
        assertEquals(transactions.get(0).amount, -1, DOUBLE_DELTA);
        assertEquals(transactions.get(1).amount, -2, DOUBLE_DELTA);
        assertEquals(transactions.get(2).amount, -3, DOUBLE_DELTA);
    }

    @Test
    public void shouldNotAllowToWithdrawNonPositiveValues() {
        Account acc = new Account(CHECKING);
        try {
            acc.withdraw(0);
            fail("Withdrawing 0 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }

        try {
            acc.withdraw(-1);
            fail("Withdrawing -1 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void shouldCorrectlyCalculateInterestForSavingsAccount() {
        Account acc = new Account(SAVINGS);
        // 0
        assertEquals(acc.interestEarned(), 0.0, DOUBLE_DELTA);
        // Less than 1000
        acc.deposit(55.55);
        assertEquals(acc.interestEarned(), 55.55 * 0.001, DOUBLE_DELTA);
        // 1000
        acc.deposit(944.45);
        assertEquals(acc.interestEarned(), 1.0, DOUBLE_DELTA);
        // More than 1000
        acc.deposit(55.55);
        assertEquals(acc.interestEarned(), 1 + 55.55 * 0.002, DOUBLE_DELTA);
    }

    @Test
    public void shouldCorrectlyCalculateInterestForMaxiSavingsAccount() {
        Account acc = new Account(MAXI_SAVINGS);
        // 0
        assertEquals(acc.interestEarned(), 0.0, DOUBLE_DELTA);
        // Less than 1000
        acc.deposit(55.55);
        assertEquals(acc.interestEarned(), 55.55 * 0.02, DOUBLE_DELTA);
        // 1000
        acc.deposit(944.45);
        assertEquals(acc.interestEarned(), 20.0, DOUBLE_DELTA);
        // More than 1000
        acc.deposit(55.55);
        assertEquals(acc.interestEarned(), 20.0 + 55.55 * 0.05, DOUBLE_DELTA);
        // 2000
        acc.deposit(944.45);
        assertEquals(acc.interestEarned(), 70.0, DOUBLE_DELTA);
        // More than 2000
        acc.deposit(55.55);
        assertEquals(acc.interestEarned(), 70.0 + 55.55 * 0.1, DOUBLE_DELTA);
    }

    @Test
    public void shouldCorrectlyCalculateInterestForCheckingAccount() {
        Account acc = new Account(CHECKING);
        // 0
        assertEquals(acc.interestEarned(), 0.0, DOUBLE_DELTA);
        // Less than 1000
        acc.deposit(55.55);
        assertEquals(acc.interestEarned(), 55.55 * 0.001, DOUBLE_DELTA);
        // 1000
        acc.deposit(944.45);
        assertEquals(acc.interestEarned(), 1.0, DOUBLE_DELTA);
    }
}
