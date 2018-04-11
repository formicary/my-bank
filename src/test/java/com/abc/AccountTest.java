package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-10;

    @Test
    public void interestOnFirstDay(){
        Account accountChecking = new AccountChecking();

        accountChecking.deposit(100.0, DateProvider.getNow(1, 1, 13, 30));
        assertEquals(0, accountChecking.interestEarned(), DOUBLE_DELTA);


        Account accountSavings = new AccountSavings();

        accountSavings.deposit(50, DateProvider.getNow(1, 1, 17, 2));
        assertEquals(0, accountSavings.interestEarned(), DOUBLE_DELTA);

        Account accountMaxiSavings = new AccountMaxiSavings();

        accountMaxiSavings.deposit(60, DateProvider.getNow(7, 8, 17, 2));
        assertEquals(0, accountMaxiSavings.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void checkingAccount() {

        Account accountChecking = new AccountChecking();

        accountChecking.deposit(100.0, DateProvider.getNow(1, 1, 13, 30));
        accountChecking.deposit(50, DateProvider.getNow(1, 4, 17, 2));
        assertEquals(0.0002739734, accountChecking.interestEarned(), DOUBLE_DELTA);

        accountChecking.withdraw(30, DateProvider.getNow(1, 6, 15, 8));
        assertEquals(0.00041096, accountChecking.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {

        Account savingsChecking = new AccountSavings();

        savingsChecking.deposit(200, DateProvider.getNow(3, 1, 13, 30));
        savingsChecking.deposit(1000, DateProvider.getNow(3, 4, 17, 2));
        assertEquals(0.0005479467, savingsChecking.interestEarned(), DOUBLE_DELTA);

        savingsChecking.withdraw(100, DateProvider.getNow(3, 5, 15, 8));
        assertEquals(0.0038356194, savingsChecking.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Account accountMaxiSavings = new AccountMaxiSavings();

        accountMaxiSavings.deposit(200, DateProvider.getNow(1, 1, 15, 0));
        //accountMaxiSavings.deposit(300, DateProvider.getNow(1, 10, 15, 0));
        accountMaxiSavings.deposit(200, DateProvider.getNow(1, 13, 15, 0));
        accountMaxiSavings.deposit(200, DateProvider.getNow(1, 14, 15, 0));
        assertEquals(0.0010958919, accountMaxiSavings.interestEarned(), DOUBLE_DELTA);



    }


}
