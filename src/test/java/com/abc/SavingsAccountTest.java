package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositValid(){
        SavingsAccount saver = new SavingsAccount();

        saver.deposit(200.0);

        assertEquals(200.0, saver.getAccountBalance(), DOUBLE_DELTA);

    }


    @Test
    public void testDepositInvalid(){
        SavingsAccount saver = new SavingsAccount();

        try{
            saver.deposit(-100.0);
            Assert.fail("Invalid deposit was accepted.");
        }catch(IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testWithdrawValid(){
        SavingsAccount saver = new SavingsAccount();

        saver.deposit(200.0);
        saver.withdraw(100.0);

        assertEquals(100.0, saver.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInvalid(){
        SavingsAccount saver = new SavingsAccount();

        saver.deposit(100.0);

        try{
            saver.withdraw(-100.0);
            Assert.fail("Invalid withdraw was accepted.");
        }catch (IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
