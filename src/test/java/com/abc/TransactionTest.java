package com.abc;

import com.abc.ENUMS.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transaction() {
        Transaction t = new Transaction(5, TransactionType.DEPOSIT);
        assertEquals(5.0,t.getAmount(),DOUBLE_DELTA);
        assertEquals(TransactionType.DEPOSIT,t.getTransactionType());


    }
}
