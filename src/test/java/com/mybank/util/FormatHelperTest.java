package com.mybank.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatHelperTest {

    @Test
    public void testNumberIs0() {
        assertEquals("0 test", FormatHelper.format(0, "test"));
    }

    @Test
    public void testNumberIs1() {
        assertEquals("1 test", FormatHelper.format(1, "test"));
    }

    @Test
    public void testNumberIs2() {
        assertEquals("2 tests", FormatHelper.format(2, "test"));
    }
}
