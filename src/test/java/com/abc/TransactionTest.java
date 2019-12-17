package com.abc;

import org.junit.Test;
//TODO: it is better for readability to avoid static imports, especially in big projects
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    // TODO: this test is useless, need to test the amount is saved into the amount field
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}
