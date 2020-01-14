package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;


public class TransactionTest {
    private static final double DOUBLE_DELTA = 1e-15;
    // Test a transaction can be created
    @Test
    public void transaction() {
        Transaction testTransaction = new Transaction(5);
        assertEquals("A transaction should have been created with supplied " +
                     "amount", 5, testTransaction.amount, DOUBLE_DELTA);
    }

    // A transaction should have a date associated with it
    @Test
    public void transactionDate() {
        Transaction testTransaction = new Transaction(50);

        Date rightNow = new DateProvider().now();
        Date dateCreated = testTransaction.date_transaction_created();
        
        // Assume dateCreated is created after or at the same time as rightNow
        assertTrue("date_transaction_created should return the date it was" + 
                    " created", (dateCreated.before(rightNow)));
    }
}
