package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private Transaction testDeposit;
    private Transaction testWithdraw;
    private final double DOUBLE_DELTA = 1e-15;

    @Before
    public void init() {
        testDeposit = new Transaction(50);
        testWithdraw = new Transaction(-50);
    }

    @Test
    public void testDeposit() {
        double expected = 50;
        double actual = testDeposit.getAmount();
        assertEquals(expected, actual, DOUBLE_DELTA);
    }

    @Test
    public void testWithdraw() {
        double expected = -50;
        double actual = testWithdraw.getAmount();
        assertEquals(expected, actual, DOUBLE_DELTA);
    }

    @Test
    public void testToString() {
        String expected = "  Deposit: $50.00";
        String actual = testDeposit.toString();
        assertEquals(expected, actual);
    }
}
