package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

public class TransactionTest {
    
	@Test
    public void testTransaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
	@Test
    public void testTransactionDate() {	
		LocalDate date = LocalDate.now();
        Transaction t = new Transaction(date, 10);
        assertEquals(t.getTransactionDate(), date);
    }
    
}
