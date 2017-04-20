package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

import com.abc.implementation.Transaction;
import com.abc.interfaces.ITransaction;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test
    public void transaction() {
        ITransaction transaction = new Transaction(5);
        assertEquals(5, transaction.getAmount(), DOUBLE_DELTA);
        
        ITransaction nextTransaction = new Transaction(10);
        
        transaction.setNextTransaction(nextTransaction);
        
        ITransaction next = transaction.getNextTransaction();
        assertEquals(10, next.getAmount(), DOUBLE_DELTA);
        
    }
}
