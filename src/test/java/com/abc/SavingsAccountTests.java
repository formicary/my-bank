package com.abc;

import com.abc.account_types.SavingsAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTests {
    private static final double DOUBLE_DELTA = 1e-15;

    // Need to decide on leading capital
    // Make a test for both?

    // Get account type?

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

        assertEquals(0.5, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        SavingsAccount account = new SavingsAccount();

        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
