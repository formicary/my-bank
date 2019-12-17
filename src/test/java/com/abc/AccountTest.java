package com.abc;

import org.junit.Assert;
import org.junit.Test;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDeposit() {
        Account account = new Account(AccountType.SAVINGS);

        account.deposit(100);
        Assert.assertEquals(1, account.transactions.size());
        Assert.assertEquals(100, account.transactions.get(0).amount, DOUBLE_DELTA);
    }

    @Test
    public void testWithdraw() {
        Account account = new Account(AccountType.SAVINGS);

        account.deposit(100);
        account.withdraw(25);
        Assert.assertEquals(2, account.transactions.size());
        Assert.assertEquals(100, account.transactions.get(0).amount, DOUBLE_DELTA);
        Assert.assertEquals(-25, account.transactions.get(1).amount, DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCantWithdrawMoreThanBalance() {
        Account account = new Account(AccountType.SAVINGS);

        account.withdraw(25);
    }

    @Test
    public void correctTransactionSum() {
        Account account = new Account(AccountType.SAVINGS);

        account.deposit(100);

        Assert.assertEquals(100, account.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testGetAccountType() {
        Account account = new Account(AccountType.SAVINGS);
        Assert.assertEquals(AccountType.SAVINGS, account.getAccountType());
    }

    @Test
    public void testCorrectInterestCalculatedChecking() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        Assert.assertEquals(0.1, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testCorrectInterestCalculatedSavings() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(3000);
        Assert.assertEquals(5, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testCorrectInterestCalculatedMaxiSavings() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(3000);
        Assert.assertEquals(170, account.interestEarned(), DOUBLE_DELTA);
    }

}
