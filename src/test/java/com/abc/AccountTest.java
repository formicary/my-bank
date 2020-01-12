package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.abc.accounts.AccountFactory;
import com.abc.accounts.AccountType;

import org.junit.Before;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    public Account checkingAccount;
    public Account savingsAccount;
    public Account maxiSavingsAccount;

    @Before
    public void setup(){
        checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deposit_negative(){
        checkingAccount.deposit(-30);
    }


    @Test(expected = IllegalArgumentException.class)
    public void withdraw_negative(){
        checkingAccount.withdraw(-30);
    }

    //Checking account tests ////////////////////////////////////////
    @Test
    public void interestEarned_checkingAccount(){
        checkingAccount.deposit(1500);
        checkingAccount.withdraw(500);
        assertEquals(1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_checkingAccount_zero(){
        assertEquals(0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }

    //TODO: implement logic handling negative sum for all Accounts
    @Test
    public void interestEarned_checkingAccount_negativeTransactions(){
        checkingAccount.withdraw(500);
        assertEquals(0, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }


    //Savings account tests /////////////////////////////////////////
    @Test
    public void interestEarned_savingsAccount_interestRate1(){
        savingsAccount.deposit(1000);
        assertEquals(1, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_savingsAccount_interestRate2(){
        savingsAccount.deposit(2000);
        assertEquals(3, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_savingsAccount_zero(){
        assertEquals(0, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_savingsAccount_maximum(){
        savingsAccount.deposit(Double.MAX_VALUE + 1);
        assertEquals(Double.MAX_VALUE + 50, savingsAccount.sumTransactions(),DOUBLE_DELTA);
    }

    //Maxi Saving account tests /////////////////////////////////////

    @Test
    public void interestEarned_maxiSavingsAccount_interestRate1(){
        maxiSavingsAccount.deposit(1000);
        assertEquals(20, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_maxiSavingsAccount_interestRate2(){
        maxiSavingsAccount.deposit(2000);
        assertEquals(70, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_maxiSavingsAccount_interestRate3(){
        maxiSavingsAccount.deposit(3000);
        assertEquals(170, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_maxiSavingsAccount_zero(){
        assertEquals(0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }


}