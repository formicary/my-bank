package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // Customer can get history of transactions
    @Test
    public void CustomerTransactionHistory_GetStatement_ShowsStatementAndTotalsForAllCustomerAccounts(){
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(checkingAccount);
        john.openAccount(savingsAccount);

        //Act
        checkingAccount.depositFunds(100.00);
        savingsAccount.depositFunds(4000.0);
        savingsAccount.withdrawFunds(200.0);

        // Assert
        assertEquals("Statement for John\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", john.getStatement());
    }

    // Customer opens one account
    @Test
    public void CustomerOpensOneAccount_GetNumberOfAccounts_ShowsOneCustomerAccount() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer john = new Customer("John");
        bank.addCustomer(john);

        // Act
        john.openAccount(checkingAccount);

        // Assert
        assertEquals(1, john.getNumberOfAccounts());
    }

    // Customer opens two accounts
    @Test
    public void CustomerOpensTwoAccounts_GetNumberOfAccounts_ShowsOneCustomerAccount() {
        // Arrange
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John");
        bank.addCustomer(john);

        // Act
        john.openAccount(checkingAccount);
        john.openAccount(savingsAccount);

        // Assert
        assertEquals(2, john.getNumberOfAccounts());
    }

    // A customer can have more than one account and word "account" is pluralised in customer summary
    @Test
    public void CustomerWithMultipleAccountsSummaryPluralised_SummeryRequest_ShowsSummary(){
        // Arrange
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.SAVINGS));
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        // Act / Assert
        assertEquals("Customer Summary\n - John (2 accounts)", bank.getCustomerSummary());
    }

    // A customer can deposit funds
    @Test
    public void CustomerMakesDeposit_SumTransactions_AccountBalanceIncreases() {
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
    public void CustomerWithdraws_SumTransactions_AccountBalanceIncreases() {
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


}
