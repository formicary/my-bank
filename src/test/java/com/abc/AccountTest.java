package com.abc;

import org.junit.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class AccountTest {

    // Tests deposits and withdrawals are summed correctly
    @Test
    public void testSum() {

        Account account = new Account(Account.CHECKING);
        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(100));
        account.withdraw(BigDecimal.valueOf(25));
        assertEquals(BigDecimal.valueOf(125).setScale(2, RoundingMode.HALF_UP), account.sumTransactions());
    }


    @Test
    public void testTransactionNo() {
        Account account = new Account(Account.CHECKING);
        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(100));
        account.withdraw(BigDecimal.valueOf(25));
        assertEquals(account.getNumberOfTransactions(), 3);
    }

    // Tests that a withdrawal will be blocked if there are insufficient funds
    @Test
    public void notEnoughFunds() {
        Account account = new Account(Account.CHECKING);
        account.deposit(BigDecimal.valueOf(50));
        account.withdraw(BigDecimal.valueOf(200));
        assertEquals(BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_UP), account.sumTransactions());
    }

    // Tests the check10DayWithdrawal function
    @Test
    public void testSimple10DayNoWithdrawals() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(100));
        account.deposit(BigDecimal.valueOf(50));
        assertFalse(account.check10DayWithdraw(3, new DateProvider()));
    }

    @Test
    public void testSimple10DayWithdrawals() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(50));
        account.withdraw(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(50));
        account.deposit(BigDecimal.valueOf(50));
        account.withdraw(BigDecimal.valueOf(50));

        assertTrue(account.check10DayWithdraw(4, new DateProvider()));
        assertFalse(account.check10DayWithdraw(1, new DateProvider()));
        assertTrue(account.check10DayWithdraw(5, new DateProvider()));
    }

    @Test
    public void test10DayDate() {
        Account account = new Account(Account.MAXI_SAVINGS);
        // Deposit and withdraw with mock transaction dates.
        account.dateProvider.setDateTime(LocalDateTime.of(2018, 12, 12, 12, 0, 0));
        account.deposit(BigDecimal.valueOf(60));
        account.dateProvider.setDateTime(LocalDateTime.of(2018, 12, 12, 12, 0, 1));
        account.withdraw(BigDecimal.valueOf(1));

        // Assert date and time has been set correctly.
        assertEquals(account.transactions.get(0).getDateTime(), LocalDateTime.of(2018, 12, 12, 12, 0, 0));
        assertEquals(account.transactions.get(1).getDateTime(), LocalDateTime.of(2018, 12, 12, 12, 0, 1));

        // Create mock account date, set to a day after the transactions and assert that there has been a
        // withdrawal in the last 10 days.
        DateProvider mockDateProvider = new DateProvider();
        mockDateProvider.setDateTime(LocalDateTime.of(2018, 12, 13, 12, 0, 0));
        assertTrue(account.check10DayWithdraw(1, mockDateProvider));

        // Create mock account date, set to 10 days after the withdrawal and assert that there have been
        // no withdrawals in the last 10 days.
        mockDateProvider.setDateTime(LocalDateTime.of(2018, 12, 30, 12, 0, 0));
        assertFalse(account.check10DayWithdraw(1, mockDateProvider));
    }

    @Test
    public void testDailySum() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.dateProvider.setDate(LocalDate.of(2018, 12, 12));
        account.deposit(BigDecimal.valueOf(100));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 12));
        account.withdraw(BigDecimal.valueOf(50));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 12));
        account.deposit(BigDecimal.valueOf(50));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 13));
        account.deposit(BigDecimal.valueOf(100));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 13));
        account.withdraw(BigDecimal.valueOf(100));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 13));
        account.deposit(BigDecimal.valueOf(100));

        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), account.sumDailyTransactions().get(0));
        assertEquals(BigDecimal.valueOf(100).setScale(2, RoundingMode.HALF_UP), account.sumDailyTransactions().get(1));
    }

    @Test
    public void testLastDailyTransaction() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.dateProvider.setDate(LocalDate.of(2018, 12, 12));
        account.deposit(BigDecimal.valueOf(100));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 13));
        account.withdraw(BigDecimal.valueOf(50));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 14));
        account.deposit(BigDecimal.valueOf(49));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 15));
        account.deposit(BigDecimal.valueOf(51));

        assertEquals(4, account.checkLastDailyTransaction().size());
    }

    @Test
    public void testCheckingInterest() {
        Account account = new Account(Account.CHECKING);
        account.deposit(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(0.1).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
        account.withdraw(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
    }

    @Test
    public void testSavingsInterest() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(1).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
        account.deposit(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(3).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
    }


    @Test
    public void maxiNoWithdraw() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(20));
        assertEquals(BigDecimal.valueOf(5).setScale(2, RoundingMode.HALF_UP), account.interestEarned());

    }

    @Test
    public void maxWithdraw() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(20));
        account.deposit(BigDecimal.valueOf(40));
        account.deposit(BigDecimal.valueOf(20));
        assertEquals(BigDecimal.valueOf(6).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
        account.withdraw(BigDecimal.valueOf(20));
        assertEquals(BigDecimal.valueOf(0.1).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
    }

    @Test
    public void testCompoundSavingsNoWithdraw() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.dateProvider.setDate(LocalDate.of(2018, 12, 12));
        account.deposit(BigDecimal.valueOf(1000));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 13));
        account.deposit(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(152.5).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
        account.dateProvider.setDate(LocalDate.of(2018, 12, 14));
    }

    @Test
    public void testCompoundSavingsWithdraw() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.dateProvider.setDate(LocalDate.of(2018, 12, 12));
        account.deposit(BigDecimal.valueOf(1000));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 13));
        account.deposit(BigDecimal.valueOf(1000));
        account.dateProvider.setDate(LocalDate.of(2018, 12, 14));
        account.withdraw(BigDecimal.valueOf(1000));
        assertEquals(BigDecimal.valueOf(153.65).setScale(2, RoundingMode.HALF_UP), account.interestEarned());
    }
}
