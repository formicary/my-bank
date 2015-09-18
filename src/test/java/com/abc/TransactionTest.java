package com.abc;

import junit.framework.TestCase;

import org.junit.Test;

public class TransactionTest extends TestCase {
    @Test
    public void testTransaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}
