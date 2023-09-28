package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.abc.utilities.enums.TransactionType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

public class TransactionTest {
    BigDecimal AMOUNT = BigDecimal.valueOf(5.00);

    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction(AMOUNT, TransactionType.DEPOSIT);
    }

    @After
    public void tearDown() {
        transaction = null;
    }

    @Test
    public void testTransaction() {
        assertTrue(transaction instanceof Transaction);
    }

    @Test
    public void testTransactionAmount() {
        BigDecimal expectedTransactionAmount = BigDecimal.valueOf(5.00);
        assertEquals(expectedTransactionAmount, transaction.getAmount());
    }

    @Test
    public void testTransactionType() {
        TransactionType expectedTransactionType = TransactionType.DEPOSIT;
        assertEquals(expectedTransactionType, transaction.getTransactionType());
    }

    @Test
    public void testTransactionDate() {
        assertNotNull(transaction.getTransactionDate());
    }
}
