package com.abc;

import org.junit.Test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class TransactionTest {
	
	// Test for the exception of a transaction with a value of 0.00
    @Test(expected = IllegalArgumentException.class)
    public void testTransaction() {
        Transaction t = new Transaction(new Money("0.0000000000000000"));
    }
    
    @Test
    public void testUniqueID(){
    	Transaction t1 = new Transaction(new Money("0.99"));
    	Transaction t2 = new Transaction(new Money("12.34"));
    	
    	assertNotEquals(t1.getID(), t2.getID());
    }
}
