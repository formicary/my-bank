package com.abc;

import com.abc.account_types.CheckingAccount;
import com.abc.shared.Constants;
import org.junit.Before;
import org.junit.Test;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;

public class CheckingAccountTests {
    CheckingAccount account;

    @Before
    public void initEach(){
        account = new CheckingAccount();
    }

    @Test
    public void getAccountType_WhenCalled_ReturnsCheckingAccount(){
        Constants.AccountTypes result = account.getAccountType();

        assertEquals(Constants.AccountTypes.CheckingAccount, result);
    }

    @Test
    public void getInterestEarned_WhenCalled_ReturnsCorrectInterest(){
        account.deposit(1000);
        double result = account.getInterestEarned();

        assertEquals(0.1, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        account.withdraw(1000);

        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
