package com.abc.features;

import org.junit.Test;

import com.abc.classes.Transaction;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    //Test to see if transaction constructor creates objects of type 'Transaction'
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, "IN");
        assertTrue(t instanceof Transaction);
    }
}
