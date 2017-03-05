package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class CustomerTest {
	
	private Customer oscar;
    private CheckingAccount checkingAccount;
    private SavingsAccount savingsAccount;
    private MaxiSavingsAccount maxiSavingsAccount;
    
	@Before
	public void setUp() {
		oscar = new Customer("Oscar");
		checkingAccount = new CheckingAccount();
	    savingsAccount = new SavingsAccount();	
	    maxiSavingsAccount = new MaxiSavingsAccount();
	}
	/**
	 * Test generation of Customer Statement with two different accounts
	 */
    @Test
    public void testApp(){
    	


        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        
        checkingAccount.setAccountNo(1);
        savingsAccount.setAccountNo(2);
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);


        assertEquals("Statement for Henry\n" +
                "\n" +
        		"Account No: 1\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Account No: 2\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    /**
     * Test opening of one account for customer
     */
    @Test
    public void testOneAccount(){
        oscar.openAccount(savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }
    /**
     * Test opening of two different account types for customer
     */
    @Test
    public void testTwoAccount(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    /**
     * Test opening of three different account types for customer
     */
    @Test
    public void testThreeAcounts() {
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(maxiSavingsAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
