package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositValid(){
        MaxiSavingAccount check = new MaxiSavingAccount();

        check.deposit(200.0);

        assertEquals(200.0, check.getAccountBalance(), DOUBLE_DELTA);

    }


    @Test
    public void testDepositInvalid(){
        MaxiSavingAccount check = new MaxiSavingAccount();

        try{
            check.deposit(-100.0);
            Assert.fail("Invalid deposit was accepted.");
        }catch(IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testWithdrawValid(){
        MaxiSavingAccount check = new MaxiSavingAccount();

        check.deposit(200.0);
        check.withdraw(100.0);

        assertEquals(100.0, check.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInvalid(){
        MaxiSavingAccount check = new MaxiSavingAccount();

        check.deposit(100.0);

        try{
            check.withdraw(-100.0);
            Assert.fail("Invalid withdraw was accepted.");
        }catch (IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
