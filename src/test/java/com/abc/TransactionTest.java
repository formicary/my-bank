package com.abc;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionTest {

    // Customer can transfer balance between their accounts
    @Test
    public void BalanceTransfer_TransferFunds_BalanceTransfersFromOneAccountToAnother() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account checkingAccount2 = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount1);
        john.openAccount(checkingAccount2);
        checkingAccount1.depositFunds(10.00);
        checkingAccount2.depositFunds(5.00);

        // Act
        john.transferFunds(checkingAccount1, checkingAccount2, 5.00);

        // Assert
        assertEquals(10.00, checkingAccount2.sumTransactions());
    }

    // Customer should not be able to transfer more than account holds
    @Test(expected = IllegalArgumentException.class)
    public void NegativeBalanceTransfer_TransferFunds_BalanceTransferShouldBeStopped() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount1 = new Account(Account.CHECKING);
        Account checkingAccount2 = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount1);
        john.openAccount(checkingAccount2);
        checkingAccount1.depositFunds(5.00);
        checkingAccount2.depositFunds(5.00);

        // Act / Assert
        john.transferFunds(checkingAccount1, checkingAccount2, 100.00);
    }

    // Test day difference calculation between two dates
    @Test
    public void DayDifferenceCalculation_DaysBetweenTwoDates_ReturnsIntegerValueOf10() throws ParseException {
        // Arrange
        Account checkingAccount = new Account(Account.CHECKING);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date startDate = format.parse("2001-01-01");
        Date endDate = format.parse("2001-01-11");

        // Act / Assert
        assertEquals(10, checkingAccount.daysBetweenTwoDates(startDate, endDate));
    }

    @Test
    public void DayDifferenceCalculation_DaysBetweenTwoDates_ReturnsIntegerValueOf5() throws ParseException {
        // Arrange
        Account checkingAccount = new Account(Account.CHECKING);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date startDate = format.parse("2001-01-01");
        Date endDate = format.parse("2001-01-06");

        // Act / Assert
        assertEquals(5, checkingAccount.daysBetweenTwoDates(startDate, endDate));
    }

    // Check if withdrawals within last 10 days are detected
    @Test
    public void WithdrawalsWithinLast10Days_WithdrawnFundsInLastTenDays_ReturnsTrue() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount);
        checkingAccount.depositFunds(1000.00, "2019-01-01");

        // Act
        // Get today's and yesterday's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        String strYesterday = format.format(yesterday);

        // Withdraw money yesterday
        checkingAccount.withdrawFunds(200, strYesterday);

        // Assert
        assertTrue(checkingAccount.withdrawnFundsInLastTenDays());
    }

    // Check if withdrawals older than 10 days are not triggering false positives
    @Test
    public void WithdrawalsOutsideLast10Days_WithdrawnFindsInLastTenDays_ReturnsFalse() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount);
        checkingAccount.depositFunds(1000.00, "2019-01-01");

        // Act
        // Get today's and fortnights's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fortnight = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 336L);
        String strFortnight = format.format(fortnight);

        // Withdraw money yesterday
        checkingAccount.withdrawFunds(200, strFortnight);

        // Assert
        assertFalse(checkingAccount.withdrawnFundsInLastTenDays());
    }

    // Check if deposits within last 10 days are triggering false positives
    @Test
    public void DepositWithinLast10Days_WithdrawnFundsInLastTenDays_ReturnsFalse() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount);
        checkingAccount.depositFunds(1000.00, "2019-01-01");

        // Act
        // Get today's and yesterday's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        String strYesterday = format.format(yesterday);

        // Withdraw money yesterday
        checkingAccount.depositFunds(200, strYesterday);

        // Assert
        assertFalse(checkingAccount.withdrawnFundsInLastTenDays());
    }

    // Maxi Savings Account Interest Rate within last 10 Days = 5%
    @Test
    public void MaxiSavingsFivePercentRateForNoWithdrawals_calculateInterestEarned_AppliesFivePercentInterest() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(maxiSavings);

        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);
        maxiSavings.depositFunds(1100.00, stringOneYearAgo);

        // Act
        // Get today's and fortnights's date
        Date fortnight = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 336L);
        String strFortnight = format.format(fortnight);

        // Withdraw money yesterday
        maxiSavings.withdrawFunds(100, strFortnight);

        // Assert
        assertEquals(50.0, maxiSavings.calculateInterestEarned());
    }

    // Maxi Savings Account Interest Rate within last 10 Days = 0.1%
    @Test
    public void MaxiSavingsNaughtOnePercentRateWithWithdrawals_calculateInterestEarned_AppliesFivePercentInterest() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavings = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(maxiSavings);
        maxiSavings.depositFunds(1100.00, "2019-01-01");

        // Act
        // Get today's and fortnights's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        String strYesterday = format.format(yesterday);

        // Withdraw money yesterday
        maxiSavings.withdrawFunds(100, strYesterday);

        // Assert
        assertEquals(1.0, maxiSavings.calculateInterestEarned());
    }
}
