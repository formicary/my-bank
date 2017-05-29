package com.abc.transaction;

import org.junit.Test;

import com.abc.transaction.Transaction;
import com.abc.transaction.Withdraw;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        TransactionI t = new Transaction(5);
        
        assertTrue(t instanceof Transaction);
    }
    
}
