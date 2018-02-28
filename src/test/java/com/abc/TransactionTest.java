package com.abc;

import org.junit.Test;

import static com.abc.TransactionType.*;
import static org.junit.Assert.assertEquals;

public class TransactionTest extends TestBase {
    @Test
    public void shouldCreateTransactionWithSpecifiedAmountAndType() {
        Transaction t1 = new Transaction(5, DEPOSIT);
        Transaction t2 = new Transaction(5, WITHDRAW);
        Transaction t3 = new Transaction(0, INTEREST);


        assertEquals(5, t1.getAmount(), DOUBLE_DELTA);
        assertEquals(DEPOSIT, t1.getTransactionType());
        assertEquals(-5, t2.getAmount(), DOUBLE_DELTA);
        assertEquals(WITHDRAW, t2.getTransactionType());
        assertEquals(0, t3.getAmount(), DOUBLE_DELTA);
        assertEquals(INTEREST, t3.getTransactionType());

    }
}
