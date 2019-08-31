package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        System.out.println(t.getDate());
        System.out.println(t.getAmount());
        Transaction n = new Transaction(5);
        System.out.println(n.getDate());
        System.out.println(n.getAmount());
        Transaction t1=t;
        System.out.println(t==n);
        System.out.println(t1==t);
        System.out.println(n.equals(t));
        assertTrue(t instanceof Transaction);
    }


}
