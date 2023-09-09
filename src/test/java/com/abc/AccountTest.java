package com.abc;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Account class.
 * These tests cover various aspects of account functionality.
 */
public class AccountTest {
    
    private static final double DOUBLE_DELTA = 0.01;
    private Account checkingAccount;

    @Before
    public void setUp() {
        checkingAccount = new Account(Account.CHECKING);
    }

    /**
     * Tests depositing a positive amount into the account.
     */
    @Test
    public void depositPositiveAmount() {
        checkingAccount.deposit(1000.0);
        assertEquals(1000.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    /**
     * Tests depositing zero amount into the account, which should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void depositZeroAmount() {
        checkingAccount.deposit(0.0);
    }

    /**
     * Tests depositing a negative amount into the account, which should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeAmount() {
        checkingAccount.deposit(-500.0);
    }

    /**
     * Tests withdrawing a valid amount from the account.
     */
    @Test
    public void withdrawValidAmount() {
        checkingAccount.deposit(1000.0);
        checkingAccount.withdraw(500.0);
        assertEquals(500.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    /**
     * Tests withdrawing zero amount from the account, which should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroAmount() {
        checkingAccount.deposit(1000.0);
        checkingAccount.withdraw(0.0);
    }

    /**
     * Tests withdrawing a negative amount from the account, which should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeAmount() {
        checkingAccount.deposit(1000.0);
        checkingAccount.withdraw(-500.0);
    }

    /**
     * Tests withdrawing more than the account balance, which should throw an exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawMoreThanBalance() {
        checkingAccount.deposit(1000.0);
        checkingAccount.withdraw(1500.0);
    }

    /**
     * Tests the compounded interest calculation for a checking account after one year.
     */
    @Test
    public void checkingAccountInterest() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1000.0);

        // Calculate interest accrued after 1 day and compound it for a year
        double dailyInterestRate = Account.CHECKING_INTEREST_RATE / 365.0;
        double expectedInterest = 1000.0 * Math.pow(1 + dailyInterestRate, 365) - 1000.0;

        double roundedExpectedInterest = Math.round(expectedInterest * 100.0) / 100.0;
        double roundedActualInterest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
        assertEquals(roundedExpectedInterest, roundedActualInterest, DOUBLE_DELTA);
    }

    /**
     * Tests the compounded interest calculation for a savings account after one year.
     */
    @Test
    public void savingsAccountInterest() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer sarah = new Customer("Sarah").openAccount(savingsAccount);
        bank.addCustomer(sarah);

        savingsAccount.deposit(1500.0);

        // Calculate interest accrued after 1 day and compound it for a year
        double roundedExpectedInterest = calculateSavingsInterest(1500.0);
        double roundedActualInterest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;

        assertEquals(roundedExpectedInterest, roundedActualInterest, DOUBLE_DELTA);
    }

    /**
     * Tests the compounded interest calculation for a maxi-savings account after one year.
     */
    @Test
    public void maxiSavingsAccountInterest() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer mike = new Customer("Mike").openAccount(maxiSavingsAccount);
        bank.addCustomer(mike);

        maxiSavingsAccount.deposit(3000.0);

        double dailyInterestRate = Account.MAXI_SAVINGS_HIGH_INTEREST_RATE / 365.0;
        double expectedInterest = 3000.0 * (Math.pow(1 + dailyInterestRate, 365) - 1);

        double roundedExpectedInterest = Math.round(expectedInterest * 100.0) / 100.0;
        double roundedActualInterest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;

        assertEquals(roundedExpectedInterest, roundedActualInterest, DOUBLE_DELTA);
    }

    /**
     * Tests the sum of transactions for an account.
     */
    @Test
    public void sumTransactions() {
        Account account = new Account(Account.CHECKING);

        account.deposit(500.0);
        account.withdraw(200.0);
        account.deposit(300.0);

        // The sum of transactions should be $500 - $200 + $300 = $600
        double expectedSum = 600.0;
        double actualSum = account.sumTransactions();

        assertEquals(expectedSum, actualSum, DOUBLE_DELTA);
    }

    /**
     * Helper method to calculate expected savings account interest.
     *
     * @param amount The amount to calculate interest for.
     * @return The expected interest amount.
     */
    public double calculateSavingsInterest(double amount) {
        double dailyLowRate = Account.SAVINGS_LOW_INTEREST_RATE / 365.0;
        double dailyHighRate = Account.SAVINGS_HIGH_INTEREST_RATE / 365.0;
        double thresholdAmount = Account.SAVINGS_THRESHOLD;

        // Calculate interest accrued after 1 day and compound it for a year
        double expectedInterest = thresholdAmount * Math.pow(1 + dailyLowRate, 365) +
                (amount - thresholdAmount) * Math.pow(1 + dailyHighRate, 365) - amount;

        double roundedExpectedInterest = Math.round(expectedInterest * 100.0) / 100.0;
        return roundedExpectedInterest;
    }
}
