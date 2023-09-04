package com.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CurrencyStringFormatterTest {

    @Test
    public void formatTest() {

        assertEquals("$123.00", CurrencyStringFormatter.format(BigDecimal.valueOf(123.00)));
    }

}