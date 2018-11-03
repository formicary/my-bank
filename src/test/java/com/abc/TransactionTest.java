package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;

	
    @Test
    public void transaction_isOfCorrectType() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void getAmount_correctAmount() {
    	Transaction t = new Transaction(5000);
    	assertEquals(5000, t.getAmount(), DOUBLE_DELTA);
    }
    
    @Test 
    public void getAmount_multipleTransactions_correctAmount() {
    	Account checkingAccount = new Account(AccountType.CHECKING);
    	
    	
    	for(int i = 0; i < 50; i++) {
    		checkingAccount.deposit(5000 + i);
    	}
    	
    	List<Transaction> t = new ArrayList<>(checkingAccount.getTransactions());
    	
    	assertEquals(50, t.size());
    			
    	for(int i = 0; i < 50; i++) {
    		assertEquals(5000 + i, t.get(i).getAmount(), DOUBLE_DELTA );
    	}
    }
    
    
    
}
