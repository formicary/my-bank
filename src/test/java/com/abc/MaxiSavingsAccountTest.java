package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositValid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        maxiSaver.deposit(200.0);

        assertEquals(200.0, maxiSaver.getAccountBalance(), DOUBLE_DELTA);

    }

    @Test
    public void testDepositInvalid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

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
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        maxiSaver.deposit(200.0);
        maxiSaver.withdraw(100.0);

        assertEquals(100.0, maxiSaver.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInvalid(){
        CheckingAccount maxiSaver = new CheckingAccount(new Customer("Bill"));

        maxiSaver.deposit(100.0);

        try{
            maxiSaver.withdraw(-100.0);
            Assert.fail("Invalid withdraw was accepted.");
        }catch (IllegalArgumentException e){
            String expectedMessage = "error: amount must be greater than zero";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testInterestRateUnder1000(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(400.00);

        assertEquals(8.00, maxiSaver.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestRateUnder2000(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(1500.00);

        assertEquals(45.00, maxiSaver.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestRateHighest(){
        MaxiSavingsAccount maxiSaver = new MaxiSavingsAccount(new Customer("Bill"));

        maxiSaver.deposit(2500.00);

        assertEquals(120.00, maxiSaver.interestEarned(), DOUBLE_DELTA);
    }
}
