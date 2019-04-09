package com.abc.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transactionTest() {
        // given
        final double amount = 12.3d;
        final LocalDate date = LocalDate.of(2019, Month.APRIL, 1);
        // when
        Transaction transaction = new Transaction(amount, date);
        // then
        assertEquals(amount, transaction.getAmount(), DOUBLE_DELTA);
        assertEquals(date, transaction.getDate());
    }

}
