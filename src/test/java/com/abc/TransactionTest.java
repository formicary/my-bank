package com.abc;

import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class TransactionTest {
    private static final double EPSILON = 1e-15;

    @Test
    public void getAmount() {
        final double AMOUNT = 5.99;
        Transaction transaction = new Transaction(AMOUNT);
        assertEquals(
                "Test that amount returned is the amount entered as a parameter",
                AMOUNT,
                transaction.getAmount(),
                EPSILON);
    }

    @Test
    public void getTransactionDate() {
        Transaction transaction = new Transaction(5);
        assertEquals(
                "Test that current date is set as transaction date",
                ZonedDateTime.now().toLocalDate(),
                transaction.getTransactionDate().toLocalDate());
    }

    @Test
    public void getDateString() {
        String expectedDatePattern =
                "^(([0-9])|([0-2][0-9])|([3][0-1]))-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)-\\d{4}$";
        Transaction transaction = new Transaction(2);
        assertTrue(
                "Test that result adheres to the expected date format",
                transaction.getDateString().matches(expectedDatePattern));
    }

    @Test
    public void getTimeString() {
        String expectedTimePattern = "(\\d{2}):(\\d{2})\\(([A-Z]{3})\\)";
        Transaction transaction = new Transaction(1);
        assertTrue(
                "Test that result adheres to expected time format",
                transaction.getTimeString().matches(expectedTimePattern));
    }

    @Test
    public void getTransactionType() {
        final String WITHDRAWAL = "Withdrawal", DEPOSIT = "Deposit";

        Transaction depositTransaction = new Transaction(1);
        assertEquals(
                "Test that transaction is a deposit when a positive parameter is passed",
                DEPOSIT,
                depositTransaction.getTransactionType());
        Transaction withdrawalTransaction = new Transaction(-1);
        assertEquals(
                "Test that transaction is a withdrawal when a negative parameter is passed",
                WITHDRAWAL,
                withdrawalTransaction.getTransactionType());
    }

    @Test
    public void setTransactionDate() {
        final ZonedDateTime NEW_DATE =
                ZonedDateTime.of(2000, 12, 20, 6, 20, 0, 0, ZoneId.of("Europe/Paris"));
        Transaction transaction = new Transaction(50);
        transaction.setTransactionDate(NEW_DATE);
        assertEquals(
                "Test that transaction date is set to parameter ",
                NEW_DATE,
                transaction.getTransactionDate());
    }

    @Test
    public void compareTo() {
        Transaction yesterday = new Transaction(5);
        yesterday.setTransactionDate(ZonedDateTime.now().minusDays(1));
        Transaction todayOne = new Transaction(5);
        Transaction todayTwo = new Transaction(-4);

        assertEquals(
                "Test that result is zero for two transactions on the same day",
                0,
                todayOne.compareTo(todayTwo));
        assertEquals(
                "Test that result is 1 if parameter transaction occurred before object transaction",
                1,
                todayOne.compareTo(yesterday));

        assertEquals(
                "Test that result is -1 if parameter transaction occurred after object transaction",
                -1,
                yesterday.compareTo(todayTwo));
    }
}
