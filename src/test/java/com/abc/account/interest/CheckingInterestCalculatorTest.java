package com.abc.account.interest;

import com.abc.account.Account;
import com.abc.account.AccountType;
import com.abc.account.TransactionType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingInterestCalculatorTest {

    private static final double DELTA = 1e-15;

    @Test
    public void When_AccountTypeIsCorrect_Expect_InterestToBeCorrectlyCalculated() {
        InterestCalculator calculator = new CheckingInterestCalculator();
        Account account = new Account(AccountType.CHECKING);
        account.deposit(1000.0, TransactionType.CUSTOMER_DEPOSIT);
        account.withdraw(200.0);

        assertEquals(0.0021917808219178, calculator.calculateDailyInterest(account), DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void When_AccountTypeIsIncompatible_Expect_ExceptionIsThrown() {
        InterestCalculator calculator = new CheckingInterestCalculator();
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(500.0, TransactionType.CUSTOMER_DEPOSIT);
        calculator.calculateDailyInterest(account);
    }
}
