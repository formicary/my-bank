package com.abc;

import com.abc.util.Money;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    // TODO add more tests
    @Test
    public void transaction() {
        Transaction t = new Transaction(new Money("5"));
        assertTrue(t instanceof Transaction);
    }
}
