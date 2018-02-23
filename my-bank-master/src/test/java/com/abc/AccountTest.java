package com.abc;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    private long DAY_IN_MS = 1000 * 60 * 60 * 24;

    private double round(double a){
        return Math.round(a * 10000.0) / 10000.0;
    } // I round the numbers to 4 decimal places as if you let the system round for me, it's rounding varies depending on context

    @Test
    public void checkingAccount() {
        Account checkingAccount = new Account(Account.AccountType.CHECKING);

        assertNull(checkingAccount.getTransactions());

        // Deposit

        try {
            checkingAccount.deposit(100);
            assertTrue(checkingAccount.getCachedBalance()== 100);
        }catch(IllegalArgumentException exc){}
        assertTrue(checkingAccount.getCachedBalance() == 100);

        /* A */ checkingAccount.getTransactions().getFirst().setTransactionDate(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));

        assertTrue(checkingAccount.getCurrentBalance() != checkingAccount.getCachedBalance()); // The cached and current balances behave separately

        assertTrue(checkingAccount.getCurrentBalance()-checkingAccount.getGrossInterestEarned()
                == checkingAccount.getCachedBalance()); // The current balance is the correct sum

        assertTrue(((checkingAccount.getCachedBalance())*(7*0.001/365) == checkingAccount.getGrossInterestEarned()));
        // Checking account interest calculated correctly

        try{
            checkingAccount.deposit(-0.99);
        }catch (IllegalArgumentException exc){
            assertTrue(checkingAccount.getCachedBalance()== 100); // Negative values rejected
        }

        try{
            checkingAccount.deposit(0.999);
        }catch (IllegalArgumentException exc){
            assertTrue(checkingAccount.getCachedBalance()== 100); // Non currency (2 decimal places) vales rejected
            /* B */ }

        //Withdrawal

        try {
            checkingAccount.withdraw(50);
            assertTrue(checkingAccount.getCachedBalance()== (50 + 100*(7*0.001/365))); // The correct amount is removed
        }catch(IllegalArgumentException exc){}

        try {
            checkingAccount.withdraw(50);
            assertFalse(checkingAccount.getCachedBalance()== 0); // Very small numbers not rounded down to 0
        }catch(IllegalArgumentException exc){}

        try {
            checkingAccount.withdraw(-1);
        }catch(IllegalArgumentException exc){
            assertTrue(checkingAccount.getTransactions().size() == 3); // All of the failed transactions weren't recorded
        }

        // The cases from A to B are synonymous within the context of withdrawal; the cases don't need to be tested twice over.

    }

    @Test
    public void savingAccount() {
        Account savingAccount = new Account(Account.AccountType.SAVINGS);

        assertNull(savingAccount.getTransactions());

        // Deposit

        try {
            savingAccount.deposit(100);
            assertTrue(savingAccount.getCachedBalance()== 100);
        }catch(IllegalArgumentException exc){}
        assertTrue(savingAccount.getCachedBalance() == 100);

        /* A */ savingAccount.getTransactions().getFirst().setTransactionDate(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));

        assertTrue(savingAccount.getCurrentBalance() != savingAccount.getCachedBalance()); // The cached and current balances behave separately

        assertTrue(savingAccount.getCurrentBalance()-savingAccount.getGrossInterestEarned()
                == savingAccount.getCachedBalance()); // The current balance is the correct sum

        assertTrue(((savingAccount.getCachedBalance())*(7*0.001/365) == savingAccount.getGrossInterestEarned()));
        // Checking account interest calculated correctly

        assertTrue(savingAccount.getCurrentBalance() == 100 + 100*(7*0.001/365));
        assertTrue(savingAccount.getCachedBalance() == 100);

        try {
            savingAccount.deposit(1000);
            assertTrue(round(savingAccount.getCachedBalance())== round((100)+ 100*(7*0.001/365) + 1000) );
        }catch(IllegalArgumentException exc){}

        assert(savingAccount.getGrossInterestEarned() == 100* (7*0.001/365)); // The 1000 is deposited today, we don't gain interest from it

        savingAccount.getTransactions().getFirst().setTransactionDate(new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));

        // Testing the scaling of interest past the breakpoint.
        assertTrue(round(100*(7*0.001/365) + ((1000 + 100 + 100*(7*0.001/365))*(7*0.002/365))) == round(savingAccount.getGrossInterestEarned()));

        // Boundary case to illustrate system rounding.
        assertFalse(100*(7*0.001/365) + ((1000 + 100 + 100*(7*0.001/365))*(7*0.002/365)) == savingAccount.getGrossInterestEarned());

        // Again, the following cases would be boilerplate code as the difference in account type does not affect deposit and withdraw methods.

    }

    @Test
    public void maxiSavingAccount() {
        Account maxiAccount = new Account(Account.AccountType.MAXI_SAVINGS);

        assertNull(maxiAccount.getTransactions());

        //Now the only thing left to test is interest cases for maxi saving account

        try {
            maxiAccount.deposit(100);
            assertTrue(maxiAccount.getCachedBalance()== 100);
        }catch(IllegalArgumentException exc){}
        assertTrue(maxiAccount.getCachedBalance() == 100);

        maxiAccount.getTransactions().getFirst().setTransactionDate(new Date(System.currentTimeMillis() - (10 * DAY_IN_MS)));


        assertTrue(((maxiAccount.getCachedBalance())*(10*0.001/365) == maxiAccount.getGrossInterestEarned()));
        // Checking account interest calculated correctly

        assertTrue(maxiAccount.getCurrentBalance() == 100 + 100*(10*0.001/365));
        assertTrue(maxiAccount.getCachedBalance() == 100);


        Account paxiAccount = new Account(Account.AccountType.MAXI_SAVINGS);

        try {
            paxiAccount.deposit(100);
        }catch(IllegalArgumentException exc){}


        paxiAccount.getTransactions().getFirst().setTransactionDate(new Date(System.currentTimeMillis() - (14 * DAY_IN_MS)));

        // Testing the scaling of interest past the breakpoint.

        paxiAccount.withdraw(1);

        // Withdraw today so we calculate the interest over the fortnight before.

        assertTrue(round(100*14*0.001/365) == round(paxiAccount.getGrossInterestEarned()));

    }









    @Test
    public void otherAccount() {
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
    }
}
