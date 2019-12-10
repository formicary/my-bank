package com.abc;

import com.abc.account_types.SavingsAccount;
import org.junit.Before;
import org.junit.Test;

import static com.abc.TestConstants.DOUBLE_DELTA;
import static org.junit.Assert.assertEquals;

public class SavingsAccountTests {
    SavingsAccount account;

    @Before
    public void initEach(){
        account = new SavingsAccount();
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceIsLessThan1000_ReturnsCorrectInterest(){
        account.deposit(500);

        double result = account.getInterestEarned();

        assertEquals(0.05, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledWithBalanceIsMoreThan1000_ReturnsCorrectInterest(){
        account.deposit(5000);

        double result = account.getInterestEarned();

        assertEquals(0.5, result, DOUBLE_DELTA);
    }

    @Test
    public void getInterestEarned_WhenCalledInNegativeBalance_Returns0(){
        account.withdraw(1000);
        double result = account.getInterestEarned();

        assertEquals(0, result, DOUBLE_DELTA);
    }
}
