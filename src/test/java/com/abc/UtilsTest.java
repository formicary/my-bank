package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void toDollarsPos() {
        assertEquals("$100.00", Utils.toDollars(100.0));
    }

    @Test
    public void toDollarsNeg() {
        assertEquals("$100.00", Utils.toDollars(-100.0));
    }

    @Test
    public void testOneWord() {
        assertEquals("1 account", Utils.formatWordPlural(1, "account"));
    }

    @Test
    public void testTwoWord() {
        assertEquals("2 accounts", Utils.formatWordPlural(2, "account"));
    }
}
