package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void zeroDeposit() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(0);
        assertEquals(0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
