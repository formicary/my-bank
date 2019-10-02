package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    private Transaction testDeposit;
    private Transaction testWithdraw;
    private final double delta = 0.000001;

    @Before
    public void init() {
        testDeposit = new Transaction(50);
        testWithdraw = new Transaction(-50);
    }

    @Test
    public void testDeposit() {
        double expected = 50;
        double actual = testDeposit.getAmount();
        assertEquals(expected, actual, delta);
    }

    @Test
    public void testWithdraw() {
        double expected = -50;
        double actual = testWithdraw.getAmount();
        assertEquals(expected, actual, delta);
    }
}
