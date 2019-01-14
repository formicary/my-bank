package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testOverdraftPrevention(){
        Account checkingAccount = new Account(Account.CHECKING);

        assertEquals(false, checkingAccount.withdraw(100));
    }

    @Test
    public void testCompoundInterest(){
        Account checkingAccount = new Account(Account.CHECKING);

        checkingAccount.deposit(36500.0);
        checkingAccount.compoundInterest();

        assertEquals(36500.1, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
