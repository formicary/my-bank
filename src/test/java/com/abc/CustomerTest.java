package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class contains unit tests for the Customer class.
 * These tests cover various aspects of customer functionality.
 */
public class CustomerTest {
    
    private static final double DOUBLE_DELTA = 0.01;

    /**
     * Tests opening one account for a customer.
     */
    @Test
    public void openOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * Tests opening multiple accounts for a customer.
     */
    @Test
    public void openMultipleAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    /**
     * Tests calculating the total interest earned by a customer.
     */
    @Test
    public void testTotalInterestEarned() {
        Customer alice = new Customer("Alice");
        
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        
        alice.openAccount(checkingAccount);
        alice.openAccount(savingsAccount);
        alice.openAccount(maxiSavingsAccount);
        
        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(2000.0);
        maxiSavingsAccount.deposit(3000.0);
        
        double expectedTotalInterest = checkingAccount.interestEarned() +
                savingsAccount.interestEarned() +
                maxiSavingsAccount.interestEarned();
        
        assertEquals(expectedTotalInterest, alice.totalInterestEarned(), DOUBLE_DELTA);
    }

    /**
     * Tests generating a customer statement.
     */
    @Test 
    public void getStatement() {
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
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
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    /**
     * Tests transferring money between customer accounts.
     */
    @Test
    public void transferBetweenAccounts() {
        Customer alice = new Customer("Alice");

        // Create accounts
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        // Open accounts for Alice
        alice.openAccount(checkingAccount);
        alice.openAccount(savingsAccount);

        // Deposit some money into accounts
        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(2000.0);

        // Transfer from checking to savings
        alice.transfer(checkingAccount, savingsAccount, 500.0);

        // Check the balances after transfer
        double checkingBalanceAfterTransfer = checkingAccount.getBalance();
        double savingsBalanceAfterTransfer = savingsAccount.getBalance();

        // Expectations after transfer:
        // Checking account balance should be $500.0
        // Savings account balance should be $2500.0
        assertEquals(500.0, checkingBalanceAfterTransfer, DOUBLE_DELTA);
        assertEquals(2500.0, savingsBalanceAfterTransfer, DOUBLE_DELTA);

        // Transfer from savings to checking
        alice.transfer(savingsAccount, checkingAccount, 1000.0);

        // Check the balances after the second transfer
        double checkingBalanceAfterSecondTransfer = checkingAccount.getBalance();
        double savingsBalanceAfterSecondTransfer = savingsAccount.getBalance();

        // Expectations after the second transfer:
        // Checking account balance should be $1500.0
        // Savings account balance should be $1500.0
        assertEquals(1500.0, checkingBalanceAfterSecondTransfer, DOUBLE_DELTA);
        assertEquals(1500.0, savingsBalanceAfterSecondTransfer, DOUBLE_DELTA);
    }
}
