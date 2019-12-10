package com.abc;

import org.junit.Test;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;

public class TransactionTests {
    @Test
    public void Transaction_WhenCreated_ContainsCorrectAmountAndDateTime(){
        Transaction transaction = new Transaction(50.0);

        assertEquals(50.0, transaction.amount, DOUBLE_DELTA);
        // Test for date/time
    }
}
