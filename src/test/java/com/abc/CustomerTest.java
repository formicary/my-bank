package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the customer class
 */
public class CustomerTest {


    /**
     * Test customer statement generation
     */
    @Test
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING, "current");
        Account savingsAccount = new Account(Account.SAVINGS, "savings");

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
     * Test opening a single account
     */
    @Test
    public void testOpeningOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS, "savings"));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * Testing opening two accounts
     */
    @Test
    public void testOpeningTwoAccounts(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS, "savings"));
        oscar.openAccount(new Account(Account.CHECKING, "current"));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    /**
     * Test opening three accounts
     */
    @Test
    public void testOpeningThreeAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS, "savings"));
        oscar.openAccount(new Account(Account.CHECKING, "current"));
        oscar.openAccount(new Account(Account.SAVINGS, "second savings"));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    /**
     * Test retrieving an account using account name
     */
    @Test
    public void testRetrievingAccount(){
        Customer bill = new Customer("Bill");
        bill.openAccount(new Account(Account.MAXI_SAVINGS, "maxi savings"));

        assertEquals(bill.getAccounts().get(0), bill.retrieveAccount("maxi savings"));
    }

    /**
     * Test trying to retrieve an account using account name that doesn't exist
     */
    @Test (expected = IllegalArgumentException.class)
    public void testRetrievingNonExistentAccount(){
        Customer bill = new Customer("Bill");
        bill.openAccount(new Account(Account.SAVINGS, "savings"));

        bill.retrieveAccount("maxi savings");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRetrievingAccountOfCustomerWithNoAccount(){
        Customer bill = new Customer("Bill");

        bill.retrieveAccount("maxi savings");
    }
}
