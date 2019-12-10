package com.abc;

import com.abc.account_types.MaxiSavingAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;
import com.abc.shared.Constants.AccountTypes;


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
    public void getInterestEarned_WhenCalledWithBalanceIsLessThan1000_ReturnsCorrectInterestAt2(){
        account.deposit(100);

        double result = account.getInterestEarned();

        assertEquals(2, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceBetween2000To3000_ReturnsCorrectInterestAt5(){
        account.deposit(2000);

        double result = account.getInterestEarned();

        assertEquals(100, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceMoreThan3000_ReturnsCorrectInterestAt10(){
        account.deposit(5000);

        double result = account.getInterestEarned();

        assertEquals(500, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
