package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_NoWithdrawalInPastTenDays_Expect_InterestRateToBeCorrect() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(2000.0);

        assertEquals(100.0, calculator.calculateInterest(account), DELTA);
    }

    @Test
    public void When_WithdrawalInPastTenDays_Expect_InterestRateToBeCorrect() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(1500.0);
        account.withdraw(100.0);

        assertEquals(1.4, calculator.calculateInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_AccountTypeIsIncompatible_Expect_ExceptionIsThrown() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.CHECKING);
        account.deposit(500.0);
        calculator.calculateInterest(account);
    }
}
