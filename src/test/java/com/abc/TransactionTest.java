package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import consts.Constants;


public class TransactionTest {
	
    @Test
    public void testCreationgOfTransactions() {
    	Transaction d, w;
    	try {
    		 d = new Transaction(-50.40, Constants.DEPOSIT);
    		fail();
    	} catch(IllegalArgumentException e) { }
    	d = new Transaction(50.40, Constants.DEPOSIT);
        assertEquals(50.40, d.getAmount(), 0);
        assertEquals(Constants.DEPOSIT, d.getTransactionType());
        try {
        	w = new Transaction(49, Constants.WITHDRAWAL);
        	fail();
        } catch(IllegalArgumentException e) { }
        w = new Transaction(-49, Constants.WITHDRAWAL);
        assertEquals(-49, w.getAmount(), 0);
        assertEquals(Constants.WITHDRAWAL, w.getTransactionType());
    }
    
}
