package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void accountBalanceIsCorrect() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100.0);

        assertEquals(account.getBalance(), 100.0);
    }

    @Test
    public void accountWithdrawsCorrectly() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100.0);
        account.withdraw(50.0);

        assertEquals(account.getBalance(), 50.0);
    }

}
