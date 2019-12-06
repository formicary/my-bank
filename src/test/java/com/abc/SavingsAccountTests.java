package com.abc;

import com.abc.account_types.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTests {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    // Need to decide on leading capital
    public void GetAccountType_WhenCalled_ReturnsSavingsAccount(){
        SavingsAccount account = new SavingsAccount();

        String result = account.getAccountType();

        assertEquals("Savings Account", result);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceIsLessThan1000_ReturnsCorrectInterest(){
        SavingsAccount account = new SavingsAccount();

        account.deposit(500);

        double result = account.getInterestEarned();

        assertEquals(0.05, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceIsMoreThan1000_ReturnsCorrectInterest(){
        SavingsAccount account = new SavingsAccount();

        account.deposit(5000);

        double result = account.getInterestEarned();

        assertEquals(0.05, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        SavingsAccount account = new SavingsAccount();

        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
