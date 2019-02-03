package com.abc;

import org.junit.Test;

import com.abc.Transaction.TRANSACTION_TYPE;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionTest {
	
    @Test
    public void testTransactionWithinLast10Days() {
        Transaction transaction = new Transaction(BigDecimal.TEN, TRANSACTION_TYPE.DEPOSIT);
        LocalDateTime now = LocalDateTime.now();
        transaction.setTransactionDate(now.minusDays(5));
        
        assertTrue(transaction.withinLast10Days());
    }
    
    @Test
    public void testTransactionNotWithinLast10Days() {
        Transaction transaction = new Transaction(BigDecimal.TEN, TRANSACTION_TYPE.DEPOSIT);
        LocalDateTime now = LocalDateTime.now();
        transaction.setTransactionDate(now.minusDays(15));
        
        assertFalse(transaction.withinLast10Days());
    }
}
