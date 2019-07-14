package com.abc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void depositNegative() {
        CheckingAccount checkingAccount = new CheckingAccount();
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Amount to deposit must be greater than zero");
        checkingAccount.deposit(-23.0);
    }

    @Test
    public void withdrawNegative() {
        CheckingAccount checkingAccount = new CheckingAccount();
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Amount to withdraw must be greater than zero");
        checkingAccount.withdraw(-23.0);
    }

    @Test
    public void withdrawInvalidAmount() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100.0);
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Withdrawal amount exceeds available funds");
        checkingAccount.withdraw(120.0);
    }

    @Test
    public void currentBalance() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(150.0);
        checkingAccount.withdraw(120.0);
        assertEquals(30.0, checkingAccount.currentBalance(), DOUBLE_DELTA);
    }

    @Test
    public void accountInterestEarned() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(150.0);
        checkingAccount.addInterest();
        checkingAccount.addInterest();
        checkingAccount.addInterest();
        checkingAccount.addInterest();
        checkingAccount.addInterest();

        double amount = 150.0;
        for (int i = 0; i < 5; i++) {
            amount += (amount * (0.001 / 365));
        }

        assertEquals(amount, checkingAccount.currentBalance(), DOUBLE_DELTA);
    }

    @Test
    public void checkingInterest() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(150.0);
        checkingAccount.addInterest();
        assertEquals(150.0 + (150.0 * (0.001 / 365)), checkingAccount.currentBalance(), DOUBLE_DELTA);
    }

    @Test
    public void savingInterestLessThan1000() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(999.0);
        savingsAccount.addInterest();
        assertEquals(999.0 + (999.0 * (0.001 / 365)), savingsAccount.currentBalance(), DOUBLE_DELTA);
    }

    @Test
    public void savingInterestMoreThan1000() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(1500.0);
        savingsAccount.addInterest();
        assertEquals(1500.0 + ((1.0 / 365) + ((1500.0 - 1000) * (0.002 / 365))),
                savingsAccount.currentBalance(),
                DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingWithdrawWithin10Days() {
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(1500.0);
        maxiSavingsAccount.withdraw(500.0);
        maxiSavingsAccount.addInterest();
        assertEquals(1000.0 + (1000.0 * (0.001 / 365)), maxiSavingsAccount.currentBalance(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingWithdrawOutsideOf10Days() {
        MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.deposit(1500.0);
        maxiSavingsAccount.addInterest();
        assertEquals(1500.00 + (1500.0 * (0.05 / 365)), maxiSavingsAccount.currentBalance(), DOUBLE_DELTA);
    }
}
