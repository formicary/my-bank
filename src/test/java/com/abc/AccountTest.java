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
        assertEquals(SAVINGS, acc.getAccountType());
        assertEquals(0, acc.getTransactions().size());
    }

    @Test
    public void shouldEmptyCreateMaxiSavingsAccount() {
        Account acc = new Account(MAXI_SAVINGS);
        assertEquals(MAXI_SAVINGS, acc.getAccountType());
        assertEquals(0, acc.getTransactions().size());
    }

    @Test
    public void shouldEmptyCreateCheckingAccount() {
        Account acc = new Account(CHECKING);
        assertEquals(CHECKING, acc.getAccountType());
        assertEquals(0, acc.getTransactions().size());
    }

    @Test
    public void shouldDepositPositiveAmountsToAccount() {
        Account acc = new Account(CHECKING);
        acc.deposit(1);
        acc.deposit(2);
        acc.deposit(3);
        List<Transaction> transactions = acc.getTransactions();
        assertEquals(3, transactions.size());
        assertEquals(1, transactions.get(0).amount, DOUBLE_DELTA);
        assertEquals(2, transactions.get(1).amount, DOUBLE_DELTA);
        assertEquals(3, transactions.get(2).amount, DOUBLE_DELTA);
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
        assertEquals(3, transactions.size());
        assertEquals(-1, transactions.get(0).amount, DOUBLE_DELTA);
        assertEquals(-2, transactions.get(1).amount, DOUBLE_DELTA);
        assertEquals(-3, transactions.get(2).amount, DOUBLE_DELTA);
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
    public void shouldCalculateInterestForSavingsAccount() {
        Account acc = new Account(SAVINGS);
        // Less than 0
        acc.withdraw(10.0);
        assertEquals(0.0, acc.interestEarned(), DOUBLE_DELTA);
        acc.deposit(10.0);
        // 0
        assertEquals(0.0, acc.interestEarned(), DOUBLE_DELTA);
        // Less than 1000
        acc.deposit(55.55);
        assertEquals(55.55 * 0.001, acc.interestEarned(), DOUBLE_DELTA);
        // 1000
        acc.deposit(944.45);
        assertEquals(1.0, acc.interestEarned(), DOUBLE_DELTA);
        // More than 1000
        acc.deposit(55.55);
        assertEquals(1 + 55.55 * 0.002, acc.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void shouldCalculateInterestForMaxiSavingsAccount() {
        Account acc = new Account(MAXI_SAVINGS);
        // Less than 0
        acc.withdraw(10.0);
        assertEquals(0.0, acc.interestEarned(), DOUBLE_DELTA);
        acc.deposit(10.0);
        // 0
        assertEquals(0.0, acc.interestEarned(), DOUBLE_DELTA);
        // Less than 1000
        acc.deposit(55.55);
        assertEquals(55.55 * 0.02, acc.interestEarned(), DOUBLE_DELTA);
        // 1000
        acc.deposit(944.45);
        assertEquals(20.0, acc.interestEarned(), DOUBLE_DELTA);
        // More than 1000
        acc.deposit(55.55);
        assertEquals(20.0 + 55.55 * 0.05, acc.interestEarned(), DOUBLE_DELTA);
        // 2000
        acc.deposit(944.45);
        assertEquals(70.0, acc.interestEarned(), DOUBLE_DELTA);
        // More than 2000
        acc.deposit(55.55);
        assertEquals(70.0 + 55.55 * 0.1, acc.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void shouldCalculateInterestForCheckingAccount() {
        Account acc = new Account(CHECKING);
        // Less than 0
        acc.withdraw(10.0);
        assertEquals(0.0, acc.interestEarned(), DOUBLE_DELTA);
        acc.deposit(10.0);
        // 0
        assertEquals(0.0, acc.interestEarned(), DOUBLE_DELTA);
        // Less than 1000
        acc.deposit(55.55);
        assertEquals(55.55 * 0.001, acc.interestEarned(), DOUBLE_DELTA);
        // 1000
        acc.deposit(944.45);
        assertEquals(1.0, acc.interestEarned(), DOUBLE_DELTA);
    }
}
