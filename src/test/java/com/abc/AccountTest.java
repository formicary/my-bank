package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // Checking Account
    // ----------------------------------------------------------------------------------------------------------------

    @Test
    public void testDeposit(){
        MaxiSavingAccount cA = new MaxiSavingAccount();

        cA.deposit(200.0);

        assertEquals(200.0, cA.getAccountBalance(), DOUBLE_DELTA);

    }


    // Savings Account
    // -----------------------------------------------------------------------------------------------------------------

    @Test
    public void testWithdraw(){
        MaxiSavingAccount cA = new MaxiSavingAccount();

        cA.deposit(1000.0);
        cA.withdraw(250.0);

        assertEquals(750.0, cA.getAccountBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawInsufficientFunds(){
        SavingsAccount sA = new SavingsAccount();
        sA.deposit(10.0);

        try {
            sA.withdraw(20.0);
            Assert.fail(); // wrong path followed, withdrawal shouldn't have been a success
        }
        catch (IllegalArgumentException e) {
            final String expected = "error: insufficient funds for withdrawal";
            assertEquals( expected, e.getMessage());
        }
    }

    @Test
    public void testWithdrawInsufficientFundsMaxi(){
        MaxiSavingsAccount maxi = new MaxiSavingsAccount();
        maxi.deposit(10.0);

        try {
            maxi.withdraw(20.0);
            Assert.fail(); // wrong path followed, withdrawal shouldn't have been a success
        }
        catch (IllegalArgumentException e) {
            final String expected = "error: insufficient funds for withdrawal";
            assertEquals( expected, e.getMessage());
        }
    }

}
