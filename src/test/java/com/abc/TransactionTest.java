package com.abc;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        LocalDateTime date = DateProvider.getInstance().now();
        Transaction t = new Transaction(5, date);
        assertTrue(t.getAmount() == 5);
        assertTrue(t.getTransactionDate().isEqual(date));
        assertEquals(t.getAnotherAccount().isPresent(), false);

    }
}
