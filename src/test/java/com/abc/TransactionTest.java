package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, DateProvider.getInstance().now());
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transactionAmount(){
        Transaction t = new Transaction(100, DateProvider.getInstance().now());
        assertEquals(100.0, t.getAmount(), Utils.DOUBLE_DELTA);
    }

}
