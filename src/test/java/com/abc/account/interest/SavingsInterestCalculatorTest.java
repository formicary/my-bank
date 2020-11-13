package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_SumOfTransactionsIsLessThanOrEqual1000_Expect_InterestToBeCorrectlyCalculated() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(100.0);

        assertEquals(0.1, calculator.calculateInterest(account), DELTA);
    }

    @Test
    public void When_SumOfTransactionsIsGreaterThan1000_Expect_InterestToBeCorrectlyCalculated() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(1500.0);

        assertEquals(2.0, calculator.calculateInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_AccountTypeIsIncompatible_Expect_ExceptionIsThrown() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(500.0);
        calculator.calculateInterest(account);
    }
}
