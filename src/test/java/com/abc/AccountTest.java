package com.abc;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

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



}
