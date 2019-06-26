package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testValidTransfer(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(500.00);

        dave.transferFunds(200.00, checking, saver);

        assertEquals(200.00, saver.accountBalance, DOUBLE_DELTA);
    }

    @Test
    public void insufficientFunds(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(10.00);

        try{
            dave.transferFunds(20.00, checking, saver);
            Transfer.performTransfer(20.00, checking, saver);
            Assert.fail("Invalid transfer was successful - test has failed");
        }catch (IllegalArgumentException e){
            String expected = "error: insufficient funds for proposed transfer";
            assertEquals(expected, e.getMessage());
        }
    }
}
