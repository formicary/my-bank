package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {
    private Date currentDate = DateProvider.getInstance().now();
    @Test
    public void transaction() {
        Transaction t = new Transaction(5,currentDate, Transaction.DEPOSIT);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transactionTypes() {
        Transaction t1 = new Transaction(-5, currentDate, Transaction.WITHDRAWAL);
        Transaction t2 = new Transaction(5, currentDate, Transaction.DEPOSIT);
        assertEquals(Transaction.WITHDRAWAL,t1.getTransactionType());
        assertEquals(Transaction.DEPOSIT,t2.getTransactionType());
    }
}
