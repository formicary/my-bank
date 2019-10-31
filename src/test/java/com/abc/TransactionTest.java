package com.abc;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void testTransactionDateIsCorrectNow() {
        Instant now = Instant.now();

        Transaction transactionTest = new Transaction(400);

        assertEquals(0, ChronoUnit.DAYS.between(now, transactionTest.getTransactionDate()));

    }
    @Test
    public void testTransactionDateIsCorrectInFuture() {
        Instant tenDaysFromNow = Instant.now().plus(10,ChronoUnit.DAYS);
        Transaction transactionTestFuture = new Transaction(10.0, tenDaysFromNow);

        assertEquals(10, ChronoUnit.DAYS.between( Instant.now(),transactionTestFuture.getTransactionDate()));
    }

    @Test
    public void testGetAmountIsCorrectForDeposits() {
        Transaction transactionTest2 = new Transaction(4);
        assertEquals(4.00, transactionTest2.getAmount(),1e-15);

    }


    @Test
    public void testGetAmountIsCorrectForWithdrawals() {
        Transaction transactionTest3 = new Transaction(-4);

        assertEquals(-4.00, transactionTest3.getAmount(),1e-15);

    }


}
