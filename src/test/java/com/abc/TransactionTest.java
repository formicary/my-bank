package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void depositTransaction() {
        Transaction transaction = new Transaction(5);
        assertEquals("deposit", transaction.getType());
    }

    @Test
    public void withdrawalTransaction() {
        Transaction transaction = new Transaction(-5);
        assertEquals("withdrawal", transaction.getType());
    }

    @Test
    public void emptyTransaction() {
        boolean exceptionCaught = false;

        try {
            Transaction transaction = new Transaction(0);
        } catch (IllegalArgumentException ex){
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

}
