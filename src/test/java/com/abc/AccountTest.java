package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import com.abc.Account.AccountType;
import com.abc.Transaction.TransactionType;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testValidDeposit() {
        Account a = new Account(AccountType.CHECKING);

        a.deposit(100.0);

        assertEquals(100.0, a.calculateBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeposit() {
        Account a = new Account(AccountType.CHECKING);

        a.deposit(-100.0);

    }

    @Test
    public void testValidWithdrawal() {
        Account a = new Account(AccountType.CHECKING);

        a.addTransaction(new Transaction(100.0, TransactionType.DEPOSIT));
        a.withdraw(50.0);

        assertEquals(50.0, a.calculateBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWithdrawal() {
        Account a = new Account(AccountType.CHECKING);

        a.addTransaction(new Transaction(50.0, TransactionType.DEPOSIT));
        a.withdraw(100.0);
    }

    @Test
    public void testDailyInterestChecking() {
        Account a = new Account(AccountType.CHECKING);

        a.addTransaction(new Transaction(100.0, TransactionType.DEPOSIT));

        assertEquals(100.0 * 0.001 / 365.25, a.getDailyInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testDailyInterestSavings() {
        Account a = new Account(AccountType.SAVINGS);

        a.addTransaction(new Transaction(2000.0, TransactionType.DEPOSIT));

        assertEquals((1000.0 * 0.001 + 1000.0 * 0.002) / 365.25, a.getDailyInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testDailyInterestMaxiRecentWithdrawal() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Account a = new Account(AccountType.MAXI_SAVINGS);

        a.addTransaction(new Transaction(200.0, TransactionType.DEPOSIT));
        a.addTransaction(new Transaction(-100.0, TransactionType.WITHDRAWAL));

        c.add(Calendar.DATE, 3);
        DateProvider.getInstance().setCustomDate(c.getTime());

        assertEquals(100.0 * 0.001 / 365.25, a.getDailyInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testDailyInterestMaxiNoRecentWithdrawal() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Account a = new Account(AccountType.MAXI_SAVINGS);

        a.addTransaction(new Transaction(200.0, TransactionType.DEPOSIT));
        a.addTransaction(new Transaction(-100.0, TransactionType.WITHDRAWAL));

        c.add(Calendar.DATE, 11);
        DateProvider.getInstance().setCustomDate(c.getTime());

        assertEquals(100.0 * 0.05 / 365.25, a.getDailyInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEligibilityEmpty() {
        Account a = new Account(AccountType.CHECKING);

        assertEquals(false, a.checkInterestEligibility());
    }

    @Test
    public void testInterestEligibilityValid() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Account a = new Account(AccountType.CHECKING);

        a.addTransaction(new Transaction(100.0, TransactionType.DEPOSIT));

        c.add(Calendar.DATE, 2);
        DateProvider.getInstance().setCustomDate(c.getTime());

        assertEquals(true, a.checkInterestEligibility());
    }

    @Test
    public void testInterestEligibilityInvalid() {
        Calendar c = Calendar.getInstance();
        DateProvider.getInstance().setCustomDate(c.getTime());

        Account a = new Account(AccountType.CHECKING);

        a.addTransaction(new Transaction(100.0, TransactionType.DEPOSIT));

        c.add(Calendar.HOUR_OF_DAY, 2);
        DateProvider.getInstance().setCustomDate(c.getTime());

        assertEquals(true, a.checkInterestEligibility());
    }

    @Test
    public void testBalanceCalculation() {
        Account a = new Account(AccountType.CHECKING);

        a.addTransaction(new Transaction(200.0, TransactionType.DEPOSIT));
        a.addTransaction(new Transaction(-100.0, TransactionType.WITHDRAWAL));
        a.addTransaction(new Transaction(1000.0, TransactionType.DEPOSIT));
        a.addTransaction(new Transaction(-10.0, TransactionType.WITHDRAWAL));

        assertEquals(1090.0, a.calculateBalance(), DOUBLE_DELTA);
    }
}