package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class TransactionTest {

    @Before
    @After
    public void reset() {
        DateProvider.setForcedDate(null);
    }

    @Test
    public void transaction() {
        DateProvider.setForcedDate(LocalDate.of(2020, 10, 5));

        Transaction transaction = new Transaction(BigDecimal.valueOf(5));

        assertEquals(0, transaction.getAmount().compareTo(BigDecimal.valueOf(5)));
        assertEquals(LocalDate.of(2020, 10, 5), transaction.getTransactionDate());
    }

    @Test
    public void isWithdrawal() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(-5));
        assertTrue(transaction.isWithdrawal());
    }

    @Test
    public void isDeposit() {
        Transaction transaction = new Transaction(BigDecimal.valueOf(5));
        assertFalse(transaction.isWithdrawal());
    }

}
