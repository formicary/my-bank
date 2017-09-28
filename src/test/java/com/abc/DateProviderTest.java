package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DateProviderTest {

    @Test
    public void testSingleton() {
        DateProvider firstDateProvider = DateProvider.getInstance();
        DateProvider secondDateProvider = DateProvider.getInstance();
        assertEquals(firstDateProvider,secondDateProvider);
        assertEquals(firstDateProvider.now(), secondDateProvider.now());
    }
}
