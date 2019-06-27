package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransferTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void fullValidTransfer(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(500.00);

        dave.transferFunds(checking, saver, 200.00);

        assertEquals(200.00, saver.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testMoneyArrivesInAccount(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(100.00);

        Transfer.performTransfer(75.00, checking, saver);

        assertEquals(75.00, saver.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testMoneyLeavesAccount(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(500.00);

        dave.transferFunds(checking, saver, 200.00);

        assertEquals(300.00, checking.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void insufficientFunds(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(10.00);

        try{
            dave.transferFunds(checking, saver, 20.00);
            Assert.fail("Invalid transfer was successful - test has failed");
        }catch (IllegalArgumentException e){
            String expected = "error: insufficient funds for proposed transfer";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    public void invalidAmount(){
        Customer dave = new Customer("Dave");
        Account checking = dave.openCheckingAccount();
        Account saver = dave.openSavingsAccount();

        checking.deposit(1000.00);

        try{
            dave.transferFunds(checking, saver, -20.00);
            Assert.fail("Invalid amount was accepted - test has failed");
        } catch (IllegalArgumentException e){
            String expected = "error: invalid amount";
            assertEquals(expected, e.getMessage());
        }
    }
}
