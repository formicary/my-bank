package com.abc;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * This class contains unit tests for the Customer class.
 * These tests cover various aspects of customer functionality.
 */
public class CustomerTest {

    private Customer alice;
    private Account checkingAccount;
    private Account savingsAccount;

    /**
     * Sets up common test objects and actions before each test method.
     */
    @Before
    public void setUp() {
        alice = new Customer("Alice");
        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
        alice.openAccount(checkingAccount);
        alice.openAccount(savingsAccount);
    }

    /**
     * Tests opening one account for a customer.
     */
    @Test
    public void openOneAccount() {
        assertEquals(1, new Customer("Oscar").openAccount(new SavingsAccount()).getNumberOfAccounts());
    }

    /**
     * Tests opening multiple accounts for a customer.
     */
    @Test
    public void openMultipleAccounts() {
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    /**
     * Tests generating a customer statement.
     */
    @Test
    public void getStatement() {
        checkingAccount.deposit(new BigDecimal("100.00"));
        savingsAccount.deposit(new BigDecimal("4000.00"));
        savingsAccount.withdraw(new BigDecimal("200.00"));

        assertEquals("Statement for Alice\n" +
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
                "Total In All Accounts $3,900.00", alice.getStatement());
    }

    /**
     * Tests transferring money between customer accounts.
     */
    @Test
    public void validTransferBetweenAccounts() {
        checkingAccount.deposit(new BigDecimal("1000.00"));
        savingsAccount.deposit(new BigDecimal("2000.00"));

        alice.transfer(checkingAccount, savingsAccount, new BigDecimal("500.00"));

        BigDecimal expectedCheckingBalance = new BigDecimal("500.00");
        BigDecimal expectedSavingsBalance = new BigDecimal("2500.00");

        assertEquals(expectedCheckingBalance, checkingAccount.getBalance());
        assertEquals(expectedSavingsBalance, savingsAccount.getBalance());
    }

    /**
     * Tests an invalid transfer that should throw an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void invalidTransferBetweenAccounts() {
        checkingAccount.deposit(new BigDecimal("1000.00"));
        savingsAccount.deposit(new BigDecimal("2000.00"));

        alice.transfer(checkingAccount, savingsAccount, new BigDecimal("5000.00"));
    }
}
