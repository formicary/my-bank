package com.abc;

import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5.0, Transaction.DEPOSIT);
        assertEquals(5.0, t.amount, 1e-15);
        assertNotNull(t.transactionDate);
    }
}
