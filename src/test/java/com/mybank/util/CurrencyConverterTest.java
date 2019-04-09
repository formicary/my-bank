package com.mybank.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyConverterTest {

    @Test
    public void testToDollarSmallAmount() {
        assertEquals("$2.00", CurrencyConverter.toDollars(2.0));
    }

    @Test
    public void testToDollarLargeAmount() {
        assertEquals("$2,000,000.00", CurrencyConverter.toDollars(2000000.0));
    }

    @Test
    public void testToDollarNegativeAmount() {
        assertEquals("$1,500,000.00", CurrencyConverter.toDollars(-1500000.0));
    }
}
