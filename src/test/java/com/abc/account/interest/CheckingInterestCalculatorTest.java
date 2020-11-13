package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_AccountTypeIsCorrect_Expect_InterestToBeCorrectlyCalculated() {
        InterestCalculator calculator = new CheckingInterestCalculator();
        Account account = new Account(AccountType.CHECKING);
        account.deposit(1000.0);
        account.withdraw(200.0);

        assertEquals(0.8, calculator.calculateInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_AccountTypeIsIncompatible_Expect_ExceptionIsThrown() {
        InterestCalculator calculator = new CheckingInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(500.0);
        calculator.calculateInterest(account);
    }
}
