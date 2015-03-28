package com.abc;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {
	
	private Transaction t;
	
	@Before
	public void setUp() {
		t = new Transaction(new BigDecimal("5"));
	}
	
    @Test
    public void transaction() {
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void transactionDate() {
    	 Date date = t.getTransactionDate();
    	 date.setTime(0);
    	 assertFalse(date.equals(t.getTransactionDate()));
    }
    
    @Test
    public void transactionAmount() {
    	BigDecimal bd = new BigDecimal("5");
    	assertTrue(t.getAmount().equals(bd));
    	assertFalse(t.getAmount() == bd);
    }
    
    @Test //TODO consider overriding equals and hashcode 
    public void checkClone() {
    	try {
			Transaction t2 = (Transaction)t.clone();		
			assertFalse(t.equals(t2));
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void checkClonedAmount() {
    	try {
			Transaction t2 = (Transaction)t.clone();
			assertTrue(t.getAmount().equals(t2.getAmount()));
			//This is allowed as BigDecimal is immutable
			assertTrue(t.getAmount() == t2.getAmount());
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void checkClonedDate() {
    	try {
			Transaction t2 = (Transaction)t.clone();
			assertTrue(t.getTransactionDate().equals(t2.getTransactionDate()));
			assertFalse(t.getTransactionDate() == t2.getTransactionDate());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
    }
}
