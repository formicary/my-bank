package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class TransactionTest {
	
	private static final double DOUBLE_DELTA = 1e-15;

	
	@Test
	public void TransactionCreationValidPos() {
		 Transaction t = new Transaction(5);
	     
		 assertTrue(t instanceof Transaction);
		 assertEquals(5.0, t.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TransactionCreationValidNeg() {
		 Transaction t = new Transaction(-5);
	     
		 assertTrue(t instanceof Transaction);
		 assertEquals(-5.0, t.amount, DOUBLE_DELTA);
	}
	
	@Test
	public void TransactionAgeOneDayOld() {
		Transaction t = new Transaction(5.0);
		
		Date oneDayOld = new Date(System.currentTimeMillis()-24*60*60*1000); //get yesterdays date
		t.getTransactionDate().setTime(oneDayOld.getTime());
		
		assertEquals(1, t.tranactionAge());
	}
	
	@Test
	public void TransactionAgeTenDayOld() {
		Transaction t = new Transaction(5.0);
		
		Date tenDayOld = new Date(System.currentTimeMillis()-24*60*60*1000*10); //get date 10 days ago
		t.getTransactionDate().setTime(tenDayOld.getTime());
		
		assertEquals(10, t.tranactionAge());
	}
	

}
