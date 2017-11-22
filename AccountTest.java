package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {


    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void sumTransactionsShouldRetrieveRightAmount() {
        Account account = new Account(Account.CHECKING);
        account.deposit(300);
        account.withdraw(100);

        assertEquals(200, account.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void accountWithNoWithdrawalsInLastTenDaysShouldReturnFalse() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.deposit(10);

        assertEquals(false,account.hasWithdrawalsInLastTenDays());
    }

    @Test
    public void accountWithWithdrawalInLastTenDaysShouldReturnFalse() {
        Account account = new Account(Account.MAXI_SAVINGS);
        account.withdraw(10);

        assertEquals(true,account.hasWithdrawalsInLastTenDays());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidAccountTypeShouldThrowException() {
        Account account = new Account(3);
        account.deposit(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void depositLessThanZeroShouldThrowException() {
        Account account = new Account(Account.SAVINGS);
        account.deposit(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void withdrawLessThanZeroShouldThrowException() {
        Account account = new Account(Account.SAVINGS);
        account.withdraw(-1);
    }
}
