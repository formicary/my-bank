package com.abc;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // A bank account can be opened
    @Test
    public void BankCustomerSummary_SummaryRequest_ShowsSummary() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John").openAccount(checkingAccount);
        bank.addCustomer(john);

        // Act / Assert
        assertEquals("Customer Summary\n - John (1 account)", bank.getCustomerSummary());
    }

    // Multiple bank accounts can be opened for same customer
    @Test
    public void BankCustomerWithMultipleAccounts_SummeryRequest_ShowsSummary() {
        // Arrange
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.SAVINGS));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        // Act / Assert
        assertEquals("Customer Summary\n - John (2 accounts)", bank.getCustomerSummary());
    }

    // Checking Account can be opened
    @Test
    public void BankCheckingAccount_TotalInterestPaid_ShowsInterest() throws ParseException {
        // Assert
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John").openAccount(checkingAccount);
        bank.addCustomer(john);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);
        checkingAccount.depositFunds(1000.00, stringOneYearAgo);

        // Assert
        assertEquals(1.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Savings Account can be opened
    @Test
    public void BankSavingsAccount_TotalInterestPaid_ShowsInterest() throws ParseException {
        // Assert
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(john);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);
        savingsAccount.depositFunds(1500.00, stringOneYearAgo);

        // Assert
        assertEquals(2.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Maxi Savings account can be opened and applies 5% increase for first $1000
    @Test
    public void BankMaxiSavingsAccountFirstBand_TotalInterestPaid_TwoPercentInterestRateApplied() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);
        maxiSavingsAccount.depositFunds(1000.00, stringOneYearAgo);

        // Assert
        assertEquals(50.00, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Maxi Savings account applies 0.1% increase for next $1000 due to withdrawal of funds
    @Test
    public void BankMaxiSavingsAccountSecondBand_TotalInterestPaid_NaughOnePercentInterestRateApplied() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
        String stringOneYearAgo = format.format(oneYearAgo);
        String stringYesterday = format.format(yesterday);
        maxiSavingsAccount.depositFunds(2000.00, stringOneYearAgo);
        maxiSavingsAccount.withdrawFunds(1000.00, stringYesterday);

        // Assert
        assertEquals(1.00, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }


    @Test
    public void BankMaxiSavingsNoWithdrawalsForTenDays_TotalInterestPaid_FivePercentInterestRateApplied() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        // Act
        maxiSavingsAccount.depositFunds(1000.00);
    }

    //TODO: To be completed
    @Test
    public void BankMaxiSavingsWithdrawalsInLastTenDays_TotalInterestPaid_LowestPercentInterestRateApplied() {
    }

    // Savings account applies 0.1% interest for first $1000
    @Test
    public void BankSavingsAccount1000_TotalInterestPaid_FirstInterestRateApplied() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(john);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);
        savingsAccount.depositFunds(1000.00, stringOneYearAgo);

        // Assert
        assertEquals(1.00, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Savings account applies 0.2% interest for more than $1000
    @Test
    public void BankSavingsAccount2000_TotalInterestPaid_SecondInterestRateApplied() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(john);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);
        savingsAccount.depositFunds(2000.00, stringOneYearAgo);

        // Assert
        assertEquals(3.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // First customer can be displayed
    @Test
    public void BankShowFirstCustomer_GetFirstCustomer_ShowsCustomerWithIndexZero() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John").openAccount(checkingAccount);
        Customer mary = new Customer("Mary").openAccount(checkingAccount);
        bank.addCustomer(john);
        bank.addCustomer(mary);

        // Act / Assert
        assertEquals("John", bank.getFirstCustomer());
    }

    // Bank manager gets list of customers and number of accounts they have
    @Test
    public void ManagerGetsListOfCustomersAndNumberOfAccounts_GetCustomerSummary_ShowsCustomerAndTheirAccountCount() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John");
        Customer mary = new Customer("Mary");
        bank.addCustomer(john);
        bank.addCustomer(mary);

        // Act
        john.openAccount(checkingAccount);
        john.openAccount(savingsAccount);
        mary.openAccount(checkingAccount);

        // Assert
        assertEquals("Customer Summary\n" +
                " - John (2 accounts)\n" +
                " - Mary (1 account)", bank.getCustomerSummary());
    }

    // Bank manager gets total interest paid by the bank on all accounts
    @Test
    public void ManagerGetsSummaryOfInterestPaid_GetTotalInterestPaid_ShowsSummaryOfAllInterestsPaidByBank() throws ParseException {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John");
        Customer mary = new Customer("Mary");
        bank.addCustomer(john);
        bank.addCustomer(mary);

        // Act
        // Get today's and last year's date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date oneYearAgo = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 8872L); // 24 x 365.5 (to account for leaps)
        String stringOneYearAgo = format.format(oneYearAgo);

        john.openAccount(checkingAccount);
        john.openAccount(maxiSavingsAccount);
        mary.openAccount(savingsAccount);
        checkingAccount.depositFunds(1000.00, stringOneYearAgo);
        maxiSavingsAccount.depositFunds(1000.00, stringOneYearAgo);
        savingsAccount.depositFunds(1000.00, stringOneYearAgo);

        // Assert
        assertEquals(52.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }
}
