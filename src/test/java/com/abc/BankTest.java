package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // A customer can open an account
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

    // A customer can have more than one account and formatting changes to plural
    @Test
    public void BankCustomerWithMultipleAccounts_SummeryRequest_ShowsSummary(){
        // Arrange
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.SAVINGS));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        // Act / Assert
        assertEquals("Customer Summary\n - John (2 accounts)", bank.getCustomerSummary());
    }

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

   @Test
   public void BankMaxiSavingsAccount1000_TotalInterestPaid_ShowsFirstInterestRate(){
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

    @Test
    public void BankMaxiSavingsAccount2000_TotalInterestPaid_ShowsFirstInterestRate(){
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

    @Test
    public void BankMaxiSavingsAccount3000_TotalInterestPaid_ShowsInterest() {
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
