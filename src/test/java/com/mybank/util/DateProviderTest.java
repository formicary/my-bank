package com.mybank.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DateProviderTest {

    @Test
    public void testEqualInstances() {
        DateProvider dateProvider1 = DateProvider.getInstance();
        DateProvider dateProvider2 = DateProvider.getInstance();
        assertEquals(dateProvider1, dateProvider2);
        assertNotNull(dateProvider1.now());
    }

    @Test
    public void testDateNotNull() {
        DateProvider dateProvider1 = DateProvider.getInstance();
        assertNotNull(dateProvider1.now());
    }
}
