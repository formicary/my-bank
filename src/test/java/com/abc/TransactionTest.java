package com.abc;

import org.junit.Test;

import com.abc.Money;;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    @Test
    public void Transaction(){
        Transaction t = new Transaction(new Money("5"));
        assertEquals(t.getType(), "deposit");
        t = new Transaction(new Money("-5"));
        assertEquals(t.getType(), "withdrawal");
    }

    @Test
    public void getAmount() {
        Transaction t = new Transaction(new Money("5"));
        assertEquals(new Money("5"), t.getAmount());
    }
}
