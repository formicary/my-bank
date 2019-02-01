package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for various aspects of the "Customer" class.
 * @author Daniel Seymour
 * @version 1.0
 */
public class CustomerTest {

	/**
	 * Tests the statement generated for a customer with one account.
	 */
	@Test 
    public void testOneAccountStatement(){

        Account checkingAccount = new Checking();

        Customer henry = new Customer("Henry").openAccount(checkingAccount);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(50.0);
        

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Total In All Accounts $50.00", henry.getStatement());
    }

	/**
	 * Tests the statement generated for a customer with two accounts.
	 */
	@Test 
    public void testTwoAccountStatement(){

        Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(50.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,850.00", henry.getStatement());
    }
	
	/**
	 * Tests the statement generated for all accounts owned by the customer.
	 * 
	 * Changed:
	 * Opened Maxi-Savings account. Added withdrawal for Checking account and deposit and withdrawal
	 * for Maxi-Savings. Text in statement being tested now reflects these changes.
	 */
    @Test 
    public void testThreeAccountStatement(){

        Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();
        Account maxiSavingsAccount = new MaxiSavings();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount)
        		.openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(50.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(2000.0);
        maxiSavingsAccount.withdraw(100.0);
        

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $2,000.00\n" +
                "  withdrawal $100.00\n" +
                "Total $1,900.00\n" +
                "\n" +
                "Total In All Accounts $5,750.00", henry.getStatement());
    }

    /**
     * Tests "getNumberOfAccounts" method for one account.
     */
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Savings());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * Tests "getNumberOfAccounts" method for two accounts.
     */
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Checking());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    /**
     * Tests "getNumberOfAccounts" method for all three types of accounts.
     * 
     * Changed:
     * ignore annotation to test so that the test is performed, opened Maxi-Savings account 
     * in addition to the other two.
     */
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Checking());
        oscar.openAccount(new MaxiSavings());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
