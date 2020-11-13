package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void interestUpTo1000IsCorrectlyCalculated() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(100.0);

        assertEquals(0.1, calculator.calculateInterest(account), DELTA);
    }

    @Test
    public void interestAbove1000IsCorrectlyCalculated() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(1500.0);

        assertEquals(2.0, calculator.calculateInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void incompatibleAccountTypeThrowsException() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(500.0);
        calculator.calculateInterest(account);
    }
}
