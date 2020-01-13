package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import com.abc.accounts.Account;
import com.abc.accounts.AccountFactory;
import com.abc.accounts.AccountType;

import org.junit.Before;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    public Account checkingAccount;
    public Account savingsAccount;
    public Account maxiSavingsAccount;

    public Date startDate;

    @Before
    public void setup(){
        checkingAccount = AccountFactory.createAccount(AccountType.CHECKING);
        savingsAccount = AccountFactory.createAccount(AccountType.SAVINGS);
        maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        startDate = new Date(calendar.getTimeInMillis());

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
        checkingAccount.deposit(1000);
        assertEquals(0.0027397260273972603, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }


    //Savings account tests /////////////////////////////////////////
    @Test
    public void interestEarned_savingsAccount_interestRate1(){
        savingsAccount.deposit(1000);
        assertEquals(0.0027397260273972603, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_savingsAccount_interestRate2(){
        savingsAccount.deposit(2000);
        assertEquals(0.008219178082191782, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_savingsAccount_zero(){
        assertEquals(0, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    //Maxi Saving account tests /////////////////////////////////////
    @Test 
    public void interestEarned_maxiSavingsAccount_noWithdrawals(){
        maxiSavingsAccount.deposit(1000);
        maxiSavingsAccount.deposit(500);
        assertEquals(0.20547945205479454, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned_maxiSavingsAccount_withdrawal(){
        maxiSavingsAccount.deposit(1000);
        maxiSavingsAccount.withdraw(500);
        assertEquals(0.0013698630136986301, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void sumTransactions_none(){
        assertEquals(0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }


}