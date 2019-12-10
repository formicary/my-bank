package com.abc;

import com.abc.account_types.MaxiSavingAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTests {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    // Need to decide on leading capital
    public void GetAccountType_WhenCalled_ReturnsMaxiSavingsAccount(){
        MaxiSavingAccount account = new MaxiSavingAccount();

        Constants.AccountTypes result = account.getAccountType();

        assertEquals(Constants.AccountTypes.MaxiSavingsAccount, result);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceIsLessThan1000_ReturnsCorrectInterestAt2(){
        MaxiSavingAccount account = new MaxiSavingAccount();

        account.deposit(100);

        double result = account.getInterestEarned();

        assertEquals(2, result, DOUBLE_DELTA);
    }

    //TODO: Fix these interest rates
    @Test
    public void getInterestEarned_WhenCalledWithBalanceBetween2000To3000_ReturnsCorrectInterestAt5(){
        MaxiSavingAccount account = new MaxiSavingAccount();

        account.deposit(2000);

        double result = account.getInterestEarned();

        assertEquals(100, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceMoreThan3000_ReturnsCorrectInterestAt10(){
        MaxiSavingAccount account = new MaxiSavingAccount();

        account.deposit(5000);

        double result = account.getInterestEarned();

        assertEquals(500, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        MaxiSavingAccount account = new MaxiSavingAccount();

        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
