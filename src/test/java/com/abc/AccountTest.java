package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains unit tests for the Account class.
 * These tests cover various aspects of account functionality.
 */
public class AccountTest {

    private Account account;
    private BigDecimal depositAmount;

    @Before
    public void setUp() {
        account = new CheckingAccount();
        depositAmount = new BigDecimal("1000.00");
    }

    // DEPOSIT TESTS

    /**
     * Tests depositing a positive amount into the account.
     */
    @Test
    public void depositPositiveAmount() {
        account.deposit(depositAmount);
        BigDecimal expectedBalance = new BigDecimal("1000.00");
        assertEquals(expectedBalance, account.getBalance());
    }

    /**
     * Tests depositing zero amount into the account, which should throw an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void depositZeroAmount() {
        BigDecimal zeroAmount = BigDecimal.ZERO;
        account.deposit(zeroAmount);
    }

    /**
     * Tests depositing a negative amount into the account, which should throw an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeAmount() {
        BigDecimal negativeAmount = new BigDecimal("-500.00");
        account.deposit(negativeAmount);
    }

    // WITHDRAW TESTS

    /**
     * Tests withdrawing a valid amount from the account.
     */
    @Test
    public void withdrawValidAmount() {
        BigDecimal withdrawalAmount = new BigDecimal("200.00");

        account.deposit(depositAmount);
        account.withdraw(withdrawalAmount);

        BigDecimal expectedBalance = new BigDecimal("800.00");

        assertEquals(expectedBalance, account.getBalance());
    }

    /**
     * Tests withdrawing zero amount from the account, which should throw an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawZeroAmount() {
        BigDecimal zeroAmount = BigDecimal.ZERO;
        account.deposit(depositAmount);
        account.withdraw(zeroAmount);
    }

    /**
     * Tests withdrawing a negative amount from the account, which should throw an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegativeAmount() {
        BigDecimal withdrawalAmount = new BigDecimal("-500.00");

        account.deposit(depositAmount);
        account.withdraw(withdrawalAmount);
    }

    /**
     * Tests withdrawing more than the account balance, which should throw an
     * exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void withdrawMoreThanBalance() {
        BigDecimal withdrawalAmount = new BigDecimal("1500.00");

        account.deposit(depositAmount);
        account.withdraw(withdrawalAmount);
    }

    /**
     * Tests the sum of transactions for an account.
     */
    @Test
    public void sumTransactions() {
        BigDecimal withdrawalAmount = new BigDecimal("200.00");
        BigDecimal additionalDeposit = new BigDecimal("300.00");

        account.deposit(depositAmount);
        account.withdraw(withdrawalAmount);
        account.deposit(additionalDeposit);

        BigDecimal expectedSum = new BigDecimal("1100.00");
        BigDecimal actualSum = account.sumTransactions();

        assertEquals(expectedSum, actualSum);
    }

    /**
     * Test getting the account type.
     */
    @Test
    public void getAccountType() {
        Account.AccountType expectedType = Account.AccountType.CHECKING;
        assertEquals(expectedType, account.getAccountType());
    }

    /**
     * Test getting the current balance of the account.
     */
    @Test
    public void getBalance() {
        account.deposit(depositAmount);
        assertEquals(depositAmount, account.getBalance());
    }

    /**
     * Test getting the date when the account was opened.
     */
    @Test
    public void getOpenedDate() {
        LocalDate openedDate = LocalDate.now(); // Set a specific opened date
        account = new CheckingAccount(); // Use a constructor that allows setting the opened date
        assertEquals(openedDate, account.getOpenedDate());
    }

    /**
     * Test getting the date of the last withdrawal.
     */
    @Test
    public void getLastWithdrawalDate() {
        assertNull(account.getLastWithdrawalDate()); // Initially, there should be no last withdrawal date

        account.deposit(depositAmount); // give the account funds to withdraw
        BigDecimal withdrawalAmount = new BigDecimal("500.00");
        account.withdraw(withdrawalAmount);

        // After a withdrawal, the last withdrawal date should be set
        LocalDate expectedWithdrawalDate = LocalDate.now();
        assertEquals(expectedWithdrawalDate, account.getLastWithdrawalDate());
    }
}
