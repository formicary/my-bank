package com.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class BigDecimalProviderTest {

    @Test
    public void getAmountFormatted_LONG() {
        assertEquals(
                BigDecimal.valueOf(12345.00).setScale(BigDecimalProvider.DECIMAL_POINTS_AMOUNT, BigDecimalProvider.ROUNDING_MODE),
                BigDecimalProvider.format(12345));
    }

    @Test
    public void getAmountFormatted_DOUBLE() {
        assertEquals(
                BigDecimal.valueOf(123.45).setScale(BigDecimalProvider.DECIMAL_POINTS_AMOUNT, BigDecimalProvider.ROUNDING_MODE),
                BigDecimalProvider.format(123.45));
    }

    @Test
    public void testGetZero() {
        assertEquals(
                BigDecimal.ZERO.setScale(BigDecimalProvider.DECIMAL_POINTS_AMOUNT, BigDecimalProvider.ROUNDING_MODE),
                BigDecimalProvider.format(0));
    }

    @Test
    public void getInterestRateFormatted() {
        assertEquals(
                BigDecimal.valueOf(0.100).setScale(BigDecimalProvider.DECIMAL_POINTS_AMOUNT, BigDecimalProvider.ROUNDING_MODE),
                BigDecimalProvider.format(0.1));
    }

}