package com.abc;

import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void testTransactionEqualsAnother(){
        Date now = new Date(System.currentTimeMillis());
        Transaction t = new Transaction(10);
        Transaction t2 = new Transaction(10, now);

        assertTrue(t.equals(t2));
    }

    @Test
    public void testTransactionExists(){
        Account checking = new Account(Account.CHECKING);
        checking.deposit(100);
        Transaction t = new Transaction(100);

        assertTrue(checking.checkIfTransactionExists(t));
    }

    @Test
    public void testAmount(){
        Transaction t = new Transaction(10);
        assertEquals(10,t.getAmount(), 0);
    }
}
