package com.abc;

import org.junit.Test;

import java.time.LocalDate;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    @Test
    public void transaction() {
        // Tests if an instance is created
        Transaction t = new Transaction(5, LocalDate.now());
        assertTrue(t instanceof Transaction);
    }

}
