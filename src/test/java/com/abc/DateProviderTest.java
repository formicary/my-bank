package com.abc;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * This class contains unit tests for the DateProvider class.
 * These tests cover various aspects of date and time functionality.
 */
public class DateProviderTest {

    /**
     * Tests the singleton behavior of the DateProvider class.
     */
    @Test
    public void testSingletonInstance() {
        DateProvider instance1 = DateProvider.getInstance();
        DateProvider instance2 = DateProvider.getInstance();

        // Ensure that both instances are the same
        assertSame(instance1, instance2);
    }

    /**
     * Tests the `now` method of the DateProvider class.
     */
    @Test
    public void testNow() {
        DateProvider dateProvider = DateProvider.getInstance();

        // Get the current date and time
        Date currentDate = dateProvider.now();

        // Check that the result is not null
        assertNotNull(currentDate);

        // Simulate some delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get the current date and time again
        Date newDate = dateProvider.now();

        // Check that the new date is not significantly different from the previous one
        assertTrue(newDate.getTime() - currentDate.getTime() < 2000); // 2 seconds difference
    }
}
