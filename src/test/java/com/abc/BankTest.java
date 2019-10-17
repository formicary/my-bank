package com.abc;

import org.junit.Test;

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

    // A customer can deposit funds
    @Test
    public void BankCustomerMakesDeposit_SumTransactions_AccountBalanceIncreases() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John").openAccount(checkingAccount);
        bank.addCustomer(john);

        //Act
        checkingAccount.depositFunds(1000.00);
        checkingAccount.depositFunds(500.50);

        // Assert
        assertEquals(1500.50, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

    // A customer can withdraw funds
    @Test
    public void BankCustomerWithdraws_SumTransactions_AccountBalanceIncreases() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John").openAccount(checkingAccount);
        bank.addCustomer(john);

        //Act
        checkingAccount.depositFunds(1000.00);
        checkingAccount.withdrawFunds(500.00);

        // Assert
        assertEquals(500.00, checkingAccount.sumTransactions(), DOUBLE_DELTA);
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
    public void BankCheckingAccount_TotalInterestPaid_ShowsInterest() {
        // Assert
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John").openAccount(checkingAccount);
        bank.addCustomer(john);

        // Act
        checkingAccount.depositFunds(100.00);

        // Assert
        assertEquals(0.1, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Savings Account can be opened
    @Test
    public void BankSavingsAccount_TotalInterestPaid_ShowsInterest() {
        // Assert
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(john);

        // Act
        savingsAccount.depositFunds(1500.00);

        // Assert
        assertEquals(2.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Maxi Savings account can be opened and applies 2% increase for first $1000
    @Test
    public void BankMaxiSavingsAccountFirstBand_TotalInterestPaid_TwoPercentInterestRateApplied() {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        // Act
        maxiSavingsAccount.depositFunds(1000.00);

        // Assert
        assertEquals(20.00, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Maxi Savings account applies 5% increase for next $1000
    @Test
    public void BankMaxiSavingsAccountSecondBand_TotalInterestPaid_FivePercentInterestRateApplied() {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        // Act
        maxiSavingsAccount.depositFunds(2000.00);

        // Assert
        assertEquals(70.00, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Maxi Savings account applies 10% for sums greater than $3000
    @Test
    public void BankMaxiSavingsAccountThirdBand_TotalInterestPaid_TenPercentInterestRateApplied() {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        // Act
        maxiSavingsAccount.depositFunds(3000.00);

        // Assert
        assertEquals(170.00, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    //TODO: To be completed
    @Test
    public void BankMaxiSavingsNoWithdrawalsForTenDays_TotalInterestPaid_FivePercentInterestRateApplied() {
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
    public void BankMaxiSavingsWithdrawalsInLastTenDays_TotalInterestPaid_LowestPercentInterestRateApplied() {}

    // Savings account applies 0.1% interest for first $1000
    @Test
    public void BankSavingsAccount1000_TotalInterestPaid_FirstInterestRateApplied() {
        // Arrange
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(john);

        // Act
        savingsAccount.depositFunds(1000);


        // Assert
        assertEquals(1.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // Savings account applies 0.2% interest for more than $1000
    @Test
    public void BankSavingsAccount2000_TotalInterestPaid_SecondInterestRateApplied() {
        // Arrange
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        bank.addCustomer(john);

        // Act
        savingsAccount.depositFunds(2000);


        // Assert
        assertEquals(3.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }

    // First customer can be dispalyed
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
}
