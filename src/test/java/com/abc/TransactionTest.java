package com.abc;

import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        Assert.assertEquals(5, t.amount, DOUBLE_DELTA);
    }
}
