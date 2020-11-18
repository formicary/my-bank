package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import com.abc.account.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_SumOfTransactionsIsLessThanOrEqual1000_Expect_InterestToBeCorrectlyCalculated() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);

        assertEquals(0.0013698630136986, calculator.calculateDailyInterest(account), DELTA);
    }

    @Test
    public void When_SumOfTransactionsIsGreaterThan1000_Expect_InterestToBeCorrectlyCalculated() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(1500.0, TransactionType.CUSTOMER_DEPOSIT);

        assertEquals(0.0054794520547945, calculator.calculateDailyInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_AccountTypeIsIncompatible_Expect_ExceptionIsThrown() {
        InterestCalculator calculator = new SavingsInterestCalculator();
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);
        calculator.calculateDailyInterest(account);
    }
}
