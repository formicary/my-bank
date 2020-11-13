package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void interestUpTo1000IsCorrectlyCalculated() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(500.0);

        assertEquals(10.0, calculator.calculateInterest(account), DELTA);
    }

    @Test
    public void interestBetween1000And2000IsCorrectlyCalculated() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(1500.0);

        assertEquals(45.0, calculator.calculateInterest(account), DELTA);
    }

    @Test
    public void interestAbove2000IsCorrectlyCalculated() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(2500.0);

        assertEquals(120.0, calculator.calculateInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incompatibleAccountTypeThrowsException() {
        InterestCalculator calculator = new MaxiSavingsInterestCalculator();
        Account account = new Account(AccountType.CHECKING);
        account.deposit(500.0);
        calculator.calculateInterest(account);
    }
}
