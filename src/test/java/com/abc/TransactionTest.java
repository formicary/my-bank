package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class TransactionTest {
    
    private static final double DOUBLE_DELTA = 1e-15;

    //test for a positive amount
    @Test
    public void transactionPositive() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    //test for a negative amount
    @Test
    public void transactionNegative() {
        Transaction t = new Transaction(-35);
        assertTrue(t instanceof Transaction);
    }
    
    //test for amount zero
    @Test
    public void transactionZero() {
        Transaction t = new Transaction(0);
        assertTrue(t instanceof Transaction);
    }
    
    //test for the function getAmount()
    @Test
    public void transactionAmount(){
        Transaction t = new Transaction(10);
       assertEquals(10,t.getAmount(),DOUBLE_DELTA);
    }
}
