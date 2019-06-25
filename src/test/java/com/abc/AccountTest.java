package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDeposit(){
        Account ac = new Account(Account.CHECKING);

        ac.deposit(200.0);

        assertEquals(200.00, ac.getAccountBalance(), DOUBLE_DELTA);

    }

    @Test
    public void testWithdraw(){
        Account ac = new Account(Account.CHECKING);

        ac.deposit(1000.0);
        ac.withdraw(250.0);

        assertEquals(750.0, ac.getAccountBalance(), DOUBLE_DELTA);
    }

}
