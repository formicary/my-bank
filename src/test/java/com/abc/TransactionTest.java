package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class TransactionTest {
	
    @Test
    public void transactionReturnsAmount() {
    	//Given
    	final int expected = 5;
    	final Date ignore = DateProvider.getInstance().now();
        
    	// When
		final Transaction transaction = new Transaction(expected, ignore);
		final double actual = transaction.getAmount();
		
		// Then
		assertEquals(expected, actual, 0.0d);
    }
    
    @Test
    public void transactionReturnsTransactionTime() {
    	//Given
    	final Date expected = Calendar.getInstance().getTime();
    	final int ignore = 0;
        
    	// When
		final Transaction transaction = new Transaction(ignore, expected);
		final Date actual = transaction.getTime();
		
		// Then
		assertEquals(expected, actual);
    }
}
