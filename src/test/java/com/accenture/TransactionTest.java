package com.accenture;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    double DOUBLE_DELTA = 1e-15;
    @Test
    public void testTransaction() {
        Transaction t = new Transaction(5,"");
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testAmount() {
        Transaction t = new Transaction(5,"");
        assertEquals(5 ,t.getAmount(),DOUBLE_DELTA);
        t = new Transaction(0,"");
        assertEquals(0 ,t.getAmount(),DOUBLE_DELTA);

    }


    @Test
    public void testTransactionDate() {
        Date transactionDate = DateProvider.getInstance().now();
        Transaction t = new Transaction(5,transactionDate,"");
        assertEquals(transactionDate ,t.getTransactionDate());

    }


    @Test
    public void testTransactionType() {
        Transaction t = new Transaction(5,"Deposit");
        assertEquals("Deposit" ,t.getTransactionType());
        t = new Transaction(0,"");
        assertEquals("" ,t.getTransactionType());
    }



}
