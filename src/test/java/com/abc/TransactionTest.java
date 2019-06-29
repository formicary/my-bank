package com.abc;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionTest {

    @Test
    public void testGetTypeString(){

        Transaction t = new Transaction(200.00, 2); // type 2 = Transaction.TRANSFER_IN

        assertEquals("transfer-in", t.getTypeString());
    }

    @Test
    public void testDetermineTypeValid(){
        Transaction t = new Transaction(200.00, 0); // type 0 = Transaction.DEPOSIT

        assertEquals("deposit", t.getTypeString());
    }

    @Test
    public void testDetermineTypeInvalid(){

        try{
            Transaction t = new Transaction(200.00, 4); // type 4 is not a valid input
            fail("fail: transaction accepted an invalid type");
        }catch (IllegalArgumentException e){
            String expected = "error: invalid transaction type used";
            assertEquals(expected, e.getMessage());
        }

    }

}
