package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositValid(){
        MaxiSavingAccount maxiSaver = new MaxiSavingAccount();

        maxiSaver.deposit(200.0);

        assertEquals(200.0, maxiSaver.getAccountBalance(), DOUBLE_DELTA);

    }


    @Test
    public void testDepositInvalid(){
        MaxiSavingAccount maxiSaver = new MaxiSavingAccount();

        try{
            maxiSaver.deposit(-100.0);
            Assert.fail("Invalid deposit was accepted.");
        }catch(IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testWithdrawValid(){
        MaxiSavingAccount maxiSaver = new MaxiSavingAccount();

        maxiSaver.deposit(200.0);
        maxiSaver.withdraw(100.0);

        assertEquals(100.0, maxiSaver.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInvalid(){
        MaxiSavingAccount maxiSaver = new MaxiSavingAccount();

        maxiSaver.deposit(100.0);

        try{
            maxiSaver.withdraw(-100.0);
            Assert.fail("Invalid withdraw was accepted.");
        }catch (IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
