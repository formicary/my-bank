package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    //Transaction must be a number
    //Positive or negative value
    //Really large number
    //Really small number


    @Test(expected = IllegalArgumentException.class)
    public void nullInput() {
        new Transaction(null);
    }

    @Test
    public void transaction() {
        Transaction t = new Transaction(new BigDecimal("100.21"));

        assertEquals(1, t.getAmount().signum());
        assertEquals(100, t.getAmount().intValue());
        assertEquals("100.21", t.getAmount().toString());
    }

    @Test
    public void negativeTransaction(){
        Transaction t = new Transaction(new BigDecimal("-99.99876"));

        assertEquals(-1, t.getAmount().signum());
        assertEquals("-99.99876", t.getAmount().toString());
    }
}
