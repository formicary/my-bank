package com.abc;

import Banking.Transaction;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TransactionTest {
	
	@Test
	public void canCreateATransaction(){
		Transaction t = new Transaction(1);
		assertEquals(t.amount,1,0);	
	}
	
	@Test (expected = Exception.class)
	public void cantCreateATransaction(){
		Object o = "a";
		Transaction t = new Transaction((int)o);
	}

}
