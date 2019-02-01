package com.abc;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-10;

    //Testing correct inputs for deposit and withdrawal
    @Test
    public void DepositTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(100);
    }

    @Test
    public void WithdrawalTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(100);

    }


    // Testing incorrect inputs for deposit and withdrawal

    @Test(expected = IllegalArgumentException.class)
    public void ZeroValueDepositTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(0);

    }

    @Test(expected = IllegalArgumentException.class)
    public void NegativeValueDepositTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(-10);
    }

    @Test (expected = IllegalArgumentException.class)
    public void BigValueDepositTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1e15);
    }

    // Testing normal values for all types of account interest
    @Test
    public void NormalCheckingAccountInterestTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1000);
        assertEquals(1000 * 0.001 / 365, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void SmallValueCheckingAccountInterestTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1);
        assertEquals(1 * 0.001 / 365, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void BigValueCheckingAccountInterestTest(){
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1e9);
        assertEquals(1e9 * 0.001 / 365, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void NormalSavingAccountInterestTest(){
        Account savingAccount = new Account(Account.SAVINGS);
        savingAccount.deposit(1000);
        assertEquals(1000 * 0.001 / 365, savingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void SmallValueSavingAccountInterestTest(){
        Account savingAccount = new Account(Account.SAVINGS);
        savingAccount.deposit(1);
        assertEquals(1 * 0.001 / 365, savingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void BigValueSavingAccountInterestTest(){
        Account savingAccount = new Account(Account.SAVINGS);
        savingAccount.deposit(1e9);
        assertEquals(((1e9 - 1000) * 0.002 / 365)
                + (1000 * 0.001 / 365), savingAccount.interestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void NormalMaxiAccountInterestTest(){
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        maxiAccount.deposit(1000);
        assertEquals(1000 * 0.05 / 365, maxiAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void SmallValueMaxiAccountInterestTest(){
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        maxiAccount.deposit(1);
        assertEquals(1 * 0.05 / 365, maxiAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void BigValueMaxiAccountInterestTest(){
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        maxiAccount.deposit(1e9);
        assertEquals(1e9 * 0.05 / 365, maxiAccount.interestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void MaxiAccountWithdrawalInterestTest(){
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        maxiAccount.deposit(1000);
        maxiAccount.withdraw(500);
        assertEquals(500 * 0.001 / 365, maxiAccount.interestEarned(), DOUBLE_DELTA);

    }


}
