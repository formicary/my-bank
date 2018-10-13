package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void testFlow() {
        Transaction t1 = new Deposit(5);
        Transaction t2 = new Withdraw(5);
        Transaction t3 = new Transfer(5,true);
        Transaction t4 = new Transfer(5,false);
        
        assertTrue(t1 instanceof Deposit);
        assertTrue(t2 instanceof Withdraw);
        assertTrue(t3 instanceof Transfer);
        
        assertTrue(t1.getFlowDirection());
        assertTrue(!t2.getFlowDirection());
        assertTrue(t3.getFlowDirection());
        assertTrue(!t4.getFlowDirection());
    }
    
    @Test
    public void testType() {
    	Transaction t1 = new Deposit(5);
        Transaction t2 = new Withdraw(5);
        Transaction t3 = new Transfer(5,true);
        
        assertEquals("Deposit",t1.getType());
        assertEquals("Withdraw",t2.getType());
        assertEquals("Transfer",t3.getType());
    }
    
    @Test
    public void testAmount() {
    	Transaction t = new Deposit(500);
    	assertEquals(500, t.getAmount(),DOUBLE_DELTA);
    }
}
