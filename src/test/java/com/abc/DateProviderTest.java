package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateProviderTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void daysThisYearTest() {
        DateProvider d = new DateProvider();
        assertEquals(365, d.daysThisYear(), DOUBLE_DELTA);
    }

    @Test
    public void yearTest() {
        DateProvider d = new DateProvider();
        assertEquals(2019, d.year());
    }
}
