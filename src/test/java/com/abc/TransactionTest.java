package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
// Rename to Tests
public class TransactionTest {
    @Test
    // Need more meaningful test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void Deposit_WhenCalledWithAmountLessThan0_ThrowsArgumentError(){
        // Create a new transaction

        // Add it to the
    }
}
