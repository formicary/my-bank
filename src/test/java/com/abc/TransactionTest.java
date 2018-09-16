package com.abc;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class TransactionTest {
	
    @Test
    public void transactionReturnsAmount() {
    	//Given
    	final int expected = 5;
    	final LocalDate ignore = null;
        
    	// When
		final Transaction transaction = new Transaction(expected, ignore);
		final double actual = transaction.getAmount();
		
		// Then
		assertEquals(expected, actual, 0.0d);
    }
    
    @Test
    public void transactionReturnsTransactionTime() {
    	//Given
    	final LocalDate expected = LocalDate.now();
    	final int ignore = 0;
        
    	// When
		final Transaction transaction = new Transaction(ignore, expected);
		final LocalDate actual = transaction.getTime();
		
		// Then
		assertEquals(expected, actual);
    }
}
