package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void transaction() {
        Transaction t = new Transaction(5d);
        assertTrue(t instanceof Transaction);
    }
}
