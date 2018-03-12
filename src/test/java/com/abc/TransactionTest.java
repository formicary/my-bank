package com.abc;

import org.junit.Test;

public class TransactionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFail(){
        Transaction transaction = new Transaction(0, "t");
    }
}
