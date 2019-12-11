package com.abc;

import com.abc.account_types.MaxiSavingAccount;
import org.junit.Before;
import org.junit.Test;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;
import com.abc.shared.Constants.AccountTypes;

import java.util.Date;


public class MaxiSavingsAccountTests {
    MaxiSavingAccount account;

    @Before
    public void initEach(){
        account = new MaxiSavingAccount();
    }

    @Test
    public void getAccountType_WhenCalled_ReturnsMaxiSavingsAccount(){
        AccountTypes result = account.getAccountType();

        assertEquals(AccountTypes.MaxiSavingsAccount, result);
    }

    @Test
    public void getInterestEarned_WhenCalledWithNoWithdrawalsInLast10Days_ReturnsCorrectCompoundInterestAt5(){
        account.deposit(50000);
        account.lastWithdrawal = new Date(Long.MIN_VALUE);

        double result = account.getInterestEarned();

        assertEquals(2563.37, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithNoWithdrawal_ReturnsCorrectCompoundInterestAt5(){
        account.deposit(10000);

        double result = account.getInterestEarned();

        assertEquals(512.67, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithWithdrawalInLast10Days_ReturnsCorrectCompoundInterestAt01(){
        account.deposit(20090);
        account.withdraw(90);

        double result = account.getInterestEarned();

        assertEquals(20.01, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
