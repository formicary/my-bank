package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
/*----------------------------------------------------------------------------- 
                            Tests for Transactions
-----------------------------------------------------------------------------*/
public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transactionAmount() {
        Transaction t = new Transaction(-500);
        assertTrue(t.getTransactionAmount()==-500);
    }
}
