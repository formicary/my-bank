package com.abc;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class TransactionTest {
    
   private final Transaction t = new Transaction(5);
  
    @Test
    public void createsATransaction() {
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void storesCorrectTransactionAmount() {
        assertTrue(t.getTransactionAmount() == 5);
    }
    
    @Test
    public void storesCorrectTransactionDate() {
        Transaction t2 = new Transaction(0);
        Date t1Date = t.getTransactionDate();
        Date t2Date = t2.getTransactionDate();
        Date after = DateProvider.getInstance().now();
        assertTrue(t2Date.getTime() - t1Date.getTime() >= 0);
        assertTrue(after.getTime() - t2Date.getTime() >= 0);
    }
}
