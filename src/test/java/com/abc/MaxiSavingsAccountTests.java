package com.abc;

import com.abc.account_types.MaxiSavingAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

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
    public void getInterestEarned_WhenCalledWithNoWithdrawalsInLast10Days_ReturnsCorrectInterestAt5(){
        account.deposit(100);
        account.lastWithdrawal = new Date(Long.MIN_VALUE);

        double result = account.getInterestEarned();

        assertEquals(5, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithNoWithdrawal_ReturnsCorrectInterestAt5(){
        account.deposit(100);

        double result = account.getInterestEarned();

        assertEquals(5, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithWithdrawalInLast10Days_ReturnsCorrectInterestAt01(){
        account.deposit(100);
        account.withdraw(90);

        double result = account.getInterestEarned();

        assertEquals(0.01, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
