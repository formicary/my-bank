package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {

    @Test // Test customer statement generation
    public void testGetStatement() {

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
                .openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(1500.0);

        assertEquals("Statement for Henry\n" + "\n" + "Checking Account\n" + "  deposit $100.00\n" + "Total $100.00\n"
                + "\n" + "Savings Account\n" + "  deposit $4,000.00\n" + "  withdrawal $200.00\n" + "Total $3,800.00\n"
                + "\n" + "Maxi Savings Account\n" + "  deposit $1,500.00\n" + "Total $1,500.00\n" + "\n"
                + "Total In All Accounts $5,400.00", henry.getStatement());
    }

    @Test // Test getNumberOfAccounts for 1 account
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test // Test getNumberOfAccounts for 2 accounts
    public void testTwoAccounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test // Test getNumberOfAccounts for 3 accounts
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test // Test get name
    public void testGetName() {
        Customer john = new Customer("John");
        john.getName();
        assertEquals("John", john.getName());
    }

    @Test // Test savings interest earned
    public void testSavingsInterestEarned() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer john = new Customer("John").openAccount(savingsAccount);
        savingsAccount.deposit(500.0);
        assertEquals(0.5, john.totalInterestEarned(), 0);
    }

    @Test // Test MaxiSavings interest earned
    public void testMaxiSavingsInterestEarned() {
        Account MaxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        Customer john = new Customer("John").openAccount(MaxiSavingsAccount);
        MaxiSavingsAccount.deposit(1500.0);
        assertEquals(45, john.totalInterestEarned(), 0);
    }

    @Test // Test openAccount
    public void testOpenAccount() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Customer sam = new Customer("Sam").openAccount(savingsAccount);
        assertNotNull(sam.openAccount(savingsAccount));
    }

    @Test // Test get number of accounts
    public void testGetNumOfAccounts() {
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        Customer sam = new Customer("Sam").openAccount(savingsAccount).openAccount(checkingAccount);
        assertEquals(2, sam.getNumberOfAccounts());
    }
}
