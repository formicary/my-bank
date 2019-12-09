package com.abc;

import com.abc.account_types.CheckingAccount;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTests {
    // Maybe a TestConstant?
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    // Need to decide on leading capital
    public void GetAccountType_WhenCalled_ReturnsCheckingAccount(){
        CheckingAccount account = new CheckingAccount();

        Constants.AccountTypes result = account.getAccountType();

        assertEquals(Constants.AccountTypes.CheckingAccount, result);
    }

    @Test
    public void getInterestEarned_WhenCalled_ReturnsCorrectInterest(){
        CheckingAccount account = new CheckingAccount();

        account.deposit(1000);
        double result = account.getInterestEarned();

        assertEquals(0.1, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        CheckingAccount account = new CheckingAccount();

        account.withdraw(1000);

        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
