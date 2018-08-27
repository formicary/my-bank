package com.abc;

import com.abc.transaction.CreditTransaction;
import com.abc.transaction.Transaction;
import com.abc.transaction.TransactionFactory;
import com.abc.transaction.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testCreation() {
        Transaction t = new TransactionFactory().createTransaction(TransactionType.CREDIT, 5.00);
        assertTrue(t.getType() == TransactionType.CREDIT);
    }

    @Test
    public void testAmountCredit() {
        Transaction t = new TransactionFactory().createTransaction(TransactionType.CREDIT, 10.00);

        assertEquals(10.00, t.getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void testAmountDebit() {
        Transaction t = new TransactionFactory().createTransaction(TransactionType.DEBIT, 15.00);

        assertEquals(15.00, t.getAmount(), DOUBLE_DELTA);
    }

    @Test
    public void testDate() {
        Transaction tOne = new TransactionFactory().createTransaction(TransactionType.DEBIT, 15.00);

        // Increase the time difference between two transactions by at least 50 ms.
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Transaction tTwo = new TransactionFactory().createTransaction(TransactionType.CREDIT, 25.00);

        long timeOne = tOne.getTransactionDate().getTime();
        long timeTwo = tTwo.getTransactionDate().getTime();
        long timeDiff = timeTwo - timeOne;

        assertNotEquals(0, timeDiff);
    }

}
