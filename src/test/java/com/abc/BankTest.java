package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void CustomerCheckingAccount_SummaryRequest_ShowsSummary() {
        // Arrange
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        // Act / Assert
        assertEquals("Customer Summary\n - John (1 account)", bank.getCustomerSummary());
    }

    //TODO: Customer with all the other account types


    @Test
    public void CustomerCheckingAccount_TotalInterestPaid_ShowsInterest() {
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
   public void CustomerSavingsAccount_TotalInterestPaid_ShowsInterest() {
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
    public void CustomerMaxiSavingsAccount_TotalInterestPaid_ShowsInterest() {
        // Arrange
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(maxiSavingsAccount);

        // Act
        maxiSavingsAccount.depositFunds(3000.00);

        // Assert
        assertEquals(170.0, bank.getTotalInterestPaid(), DOUBLE_DELTA);
    }
}
