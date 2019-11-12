package com.abc;

import org.junit.Test;

import java.time.LocalDateTime;

import static com.abc.Bank.DOLLAR;
import static com.abc.BankTest.DOUBLE_DELTA;
import static com.abc.Account.*;
import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testNegativeDeposit() {
        Account account = new Account(AccountType.SAVINGS);
        try {
            account.deposit(-100.521);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount must be greater than zero!", e.getMessage());
        }
    }

    @Test
    public void testZeroDeposit() {
        Account account = new Account(AccountType.SAVINGS);
        try {
            account.deposit(0d);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount must be greater than zero!", e.getMessage());
        }
    }

    @Test
    public void testNegativeWithdrawal() {
        Account account = new Account(AccountType.SAVINGS);
        try {
            account.withdraw(-100.521);
            fail("Exception not thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount must be greater than zero!", e.getMessage());
        }
    }

    @Test
    public void testZeroWithdrawal() {
        Account account = new Account(AccountType.SAVINGS);
        try {
            account.withdraw(0d);
            fail("Exception not thrown!");
        } catch (IllegalArgumentException e) {
            assertEquals("Amount must be greater than zero!", e.getMessage());
        }
    }

    @Test
    public void testInsufficientBalance() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(100.3764);
        assertFalse("Balance is insufficient for a withdrawal.", account.withdraw(100.38));
    }

    @Test
    public void testBalance() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(100.7);
        account.withdraw(50.24);
        account.withdraw(20.093);
        assertEquals(account.getBalance(), account.getTransactions().stream().mapToDouble(Transaction::getAmount).sum(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnChecking() { //0.1%
        Account account = new Account(AccountType.CHECKING);
        account.deposit(3500d);
        assertEquals(3500 * Rate.CHECKING.getRate(), account.getInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnSavings() { //0.1% for the first $1,000 then 0.2%
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(3500d);
        assertEquals(1000 * Rate.SAVINGS_MIN.getRate() + 2500 * Rate.SAVINGS_MAX.getRate(), account.getInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnMaxiSavingsI() { //2% for the first $1,000 then 5% for the next $1,000 then 10%
        Account account = new Account(AccountType.MAXI_SAVINGS_I);
        account.deposit(3500d);
        assertEquals(1000 * Rate.MAXI_SAVINGS_I_MIN.getRate() + 1000 * Rate.MAXI_SAVINGS_I_MID.getRate() + 1500 * Rate.MAXI_SAVINGS_I_MAX.getRate(), account.getInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnMaxiSavingsII() { //5% assuming no withdrawals in the past 10 days otherwise 0.1%
        Account account1 = new Account(AccountType.MAXI_SAVINGS_II);
        Account account2 = new Account(AccountType.MAXI_SAVINGS_II);

        //Test period [10/26...11/5], test dates 11/5, 11/4
        //1. Only deposit
        account1.deposit(1000d, LocalDateTime.of(2019, 10, 1, 14, 57));
        account1.withdraw(100d, LocalDateTime.of(2019, 10, 25, 14, 39));
        account1.deposit(100d, LocalDateTime.of(2019, 10, 26, 10, 14));
        account1.deposit(100d, LocalDateTime.of(2019, 10, 27, 12, 37));
        account1.deposit(100d, LocalDateTime.of(2019, 11, 4, 17, 5));
        account1.deposit(100d, LocalDateTime.of(2019, 11, 5, 9, 42));
        System.out.println("Test Account 1: " + DOLLAR.format(account1.getBalance()));
        assertEquals(1100 * Rate.MAXI_SAVINGS_II_MAX.getRate() + 200 * Rate.MAXI_SAVINGS_II_MIN.getRate(), account1.getInterestEarned(), DOUBLE_DELTA);

        //2. Deposit & withdrawal on same day
        account2.deposit(1000d, LocalDateTime.of(2019, 10, 1, 14, 57));
        account2.withdraw(100d, LocalDateTime.of(2019, 10, 25, 14, 39));
        account2.deposit(100d, LocalDateTime.of(2019, 10, 25, 16, 2));
        account2.deposit(100d, LocalDateTime.of(2019, 10, 26, 10, 14));
        account2.deposit(100d, LocalDateTime.of(2019, 10, 27, 12, 37));
        account2.deposit(100d, LocalDateTime.of(2019, 11, 4, 11, 25));
        account2.deposit(100d, LocalDateTime.of(2019, 11, 5, 9, 42));
        account2.withdraw(100d, LocalDateTime.of(2019, 11, 5, 17, 5));
        System.out.println("\nTest Account 2: " + DOLLAR.format(account2.getBalance()));
        assertEquals(1000 * Rate.MAXI_SAVINGS_II_MAX.getRate() + 300 * Rate.MAXI_SAVINGS_II_MIN.getRate(), account2.getInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testStatement() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(2100.6479);
        account.withdraw(50.8);
        account.withdraw(20.0);
        assertEquals("Checking Account\n"
                + "  deposit $2,100.65\n"
                + "  withdrawal $50.80\n"
                + "  withdrawal $20.00\n"
                + "Total $2,029.85"
                , account.printStatement());
    }

}
