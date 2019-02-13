package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void transactionDepositTest() {
        Transaction t = new Transaction(5);
        
        assertEquals( t.getAmount(), 5, DOUBLE_DELTA);      
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void transactionWidthdrawTest() {
    	Transaction t = new Transaction(-5);
    	boolean isWidthdraw = t.isWithdraw();
        assertEquals( t.getAmount(), -5, DOUBLE_DELTA);      
        assertTrue(isWidthdraw == true); 
    }
}
