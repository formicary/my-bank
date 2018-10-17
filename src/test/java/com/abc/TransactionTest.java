package com.abc;

import org.junit.Test;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;

public class TransactionTest {

    // Creates transactions and tests that they are correctly labelled as Deposits or Withdrawals
    @Test
    public void transaction() {
        Transaction t = new Transaction(BigDecimal.valueOf(5));
        assertEquals(t.getType(), Transaction.DEPOSIT);
        Transaction t2 = new Transaction(BigDecimal.valueOf(-5));
        assertEquals(t2.getType(), Transaction.WITHDRAWAL);
    }
}
