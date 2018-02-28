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
        assertEquals(1, transactions.get(0).getAmount(), DOUBLE_DELTA);
        assertEquals(2, transactions.get(1).getAmount(), DOUBLE_DELTA);
        assertEquals(3, transactions.get(2).getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void shouldNotAllowToDepositNonPositiveValues() {
        Account acc = new Account(CHECKING);
        try {
            acc.deposit(0);
            fail("Depositing 0 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
        }

        try {
            acc.deposit(-1);
            fail("Depositing -1 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
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
        assertEquals(-1, transactions.get(0).getAmount(), DOUBLE_DELTA);
        assertEquals(-2, transactions.get(1).getAmount(), DOUBLE_DELTA);
        assertEquals(-3, transactions.get(2).getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void shouldNotAllowToWithdrawNonPositiveValues() {
        Account acc = new Account(CHECKING);
        try {
            acc.withdraw(0);
            fail("Withdrawing 0 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
        }

        try {
            acc.withdraw(-1);
            fail("Withdrawing -1 should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("amount must be greater than zero", e.getMessage());
        }
    }


    public static class SavingsAccount {
        @Test
        public void shouldCalculateInterestForNegativeBalance() {
            Account acc = new Account(SAVINGS);
            acc.withdraw(10.0);
            acc.calculateInterest();
            assertEquals(0.0, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestForZeroBalance() {
            Account acc = new Account(SAVINGS);
            acc.calculateInterest();
            assertEquals(0.0, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestForFirstTier() {
            Account acc = new Account(SAVINGS);
            acc.deposit(55.55);
            acc.calculateInterest();
            assertEquals(55.55 * 0.001, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestForSecondTier() {
            Account acc = new Account(SAVINGS);
            acc.deposit(1055.55);
            acc.calculateInterest();
            assertEquals(1 + 55.55 * 0.002, acc.getInterestEarned(), DOUBLE_DELTA);
        }

    }

    public static class MaxiSavings {
        @Test
        public void shouldCalculateInterestForNegativeBalance() {
            Account acc = new Account(MAXI_SAVINGS);
            acc.withdraw(10.0);
            acc.calculateInterest();
            assertEquals(0.0, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestForZeroBalance() {
            Account acc = new Account(MAXI_SAVINGS);
            acc.calculateInterest();
            assertEquals(0.0, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestIfNoWithdrawalsMadeIn10Days() {
            Account acc = new Account(MAXI_SAVINGS);
            acc.deposit(55.55);
            acc.calculateInterest();
            assertEquals(55.55 * 0.05, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestIfWithdrawalsMadeIn10Days() {
            Account acc = new Account(MAXI_SAVINGS);
            acc.withdraw(1.0);
            acc.deposit(1056.55);
            acc.calculateInterest();
            assertEquals(1055.55 * 0.0001, acc.getInterestEarned(), DOUBLE_DELTA);
        }

    }


    public static class CheckingAccount {
        @Test
        public void shouldCalculateInterestForNegativeBalance() {
            Account acc = new Account(CHECKING);
            acc.withdraw(10.0);
            acc.calculateInterest();
            assertEquals(0.0, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestForZeroBalance() {
            Account acc = new Account(CHECKING);
            acc.calculateInterest();
            assertEquals(0.0, acc.getInterestEarned(), DOUBLE_DELTA);
        }

        @Test
        public void shouldCalculateInterestForFirstTier() {
            Account acc = new Account(CHECKING);
            acc.deposit(55.55);
            acc.calculateInterest();
            assertEquals(55.55 * 0.001, acc.getInterestEarned(), DOUBLE_DELTA);
        }

    }
}
