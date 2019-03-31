package com.abc;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TransactionTest {
    private Date currentDate = DateProvider.getInstance().now();
    @Test
    public void transaction() {
        Transaction t = new Transaction(5,currentDate, Transaction.Deposit);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void transactionTypes() {
        Transaction t1 = new Transaction(-5, currentDate, Transaction.Withdrawel);
        Transaction t2 = new Transaction(5, currentDate, Transaction.Deposit);
        assertEquals(Transaction.Withdrawel,t1.getTransactionType());
        assertEquals(Transaction.Deposit,t2.getTransactionType());
    }
}
