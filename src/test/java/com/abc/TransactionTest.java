package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Account a = new AccountChecking();
        a.deposit(12, DateProvider.getNow());
        a.withdraw(2, DateProvider.getNow());
        Transaction t1 = a.getTransactions().get(0);
        Transaction t2 = a.getTransactions().get(1);

        assertEquals(12, t1.getAmount(), 0);
        assertEquals(12, t1.getBalance(), 0);

        assertEquals(-2, t2.getAmount(), 0);
        assertEquals(10, t2.getBalance(), 0);

        assertTrue("Transactions are made on the same date", t1.getDay() == t2.getDay());

    }
}
