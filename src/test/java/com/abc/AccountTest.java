package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositValid(){

        CheckingAccount cA = new CheckingAccount(new Customer("Bill"));

        cA.deposit(200.0);

        assertEquals(200.0, cA.getAccountBalance(), DOUBLE_DELTA);

    }

    @Test
    public void testWithdrawValid(){
        CheckingAccount cA = new CheckingAccount(new Customer("Bill"));

        cA.deposit(1000.0);
        cA.withdraw(250.0);

        assertEquals(750.0, cA.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInsufficientFunds(){
        SavingsAccount sA = new SavingsAccount(new Customer("Bill"));
        sA.deposit(10.0);

        try {
            sA.withdraw(20.0);
            Assert.fail("fail: transfer was accepted despite sender having insufficient funds");
        }
        catch (IllegalArgumentException e) {
            final String expected = "error: insufficient funds for withdrawal";
            assertEquals( expected, e.getMessage());
        }
    }
}
