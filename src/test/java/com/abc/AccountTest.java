package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void getAccount(){
        Account account = new Account();
        assertEquals("Current Account\n", account.getAccountType());
    }

    @Test
    public void getMaxiSavings(){
        Account account = new MaxiSavingsAccount();
        assertEquals("Maxi Savings Account\n", account.getAccountType());
    }

    @Test
    public void getSavings(){
        Account account = new SavingsAccount();
        assertEquals("Savings Account\n", account.getAccountType());
    }

    @Test
    public void getChecking(){
        Account account = new CheckingAccount();
        assertEquals("Checking Account\n", account.getAccountType());
    }

    @Test
    public void maxiSavingsInterestEarned5(){
        Account msAccount = new MaxiSavingsAccount();
        msAccount.deposit(800.0);

        assertEquals(10.95, msAccount.interestEarned(), 1);
    }

    @Test
    public void maxiSavingsInterestEarnedpoint1(){
        Account msAccount = new MaxiSavingsAccount();
        msAccount.deposit(800.0);
        msAccount.withdraw(500.0);

        assertEquals(0.08, msAccount.interestEarned(), 1);
    }
}
