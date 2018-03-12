package com.abc;

import org.junit.Test;

/* Written by Tunc Demircan */
public class TransactionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorFail(){
        Transaction transaction = new Transaction(0, "t");
    }
}
