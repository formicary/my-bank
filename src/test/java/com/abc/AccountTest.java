package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
/*----------------------------------------------------------------------------- 
                            Tests for Accounts
-----------------------------------------------------------------------------*/
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testFivePercent() {
        MaxiSavingsAccount maxi=new MaxiSavingsAccount();
        maxi.deposit(1000);

        assertEquals(50, maxi.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testDefault() {
        MaxiSavingsAccount maxi = new MaxiSavingsAccount();
        maxi.deposit(1000);
        maxi.withdraw(100);

        assertEquals(0.9, maxi.interestEarned(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidDeposit() {
        new CheckingAccount().deposit(-500);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidWithdraw() {
        new CheckingAccount().withdraw(-500);
    }
}
