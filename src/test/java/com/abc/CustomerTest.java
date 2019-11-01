package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Requirements tested:
 * A customer can open an account
 * A customer can deposit / withdraw funds from an account
 * A customer can request a statement that shows transactions and totals for each of their accounts
 * A customer can transfer between their accounts
 * @author Andreas Neokleous
 */
public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;
    /**
     * Test constructor, openAccount, getStatement methods of Customer.java
     * Test constructor, deposit, withdraw methods of Account.java
     */
    @Test 
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
          
        // Make deposits/withdraws from Customer object.
        henry.getAccount(Account.CHECKING).deposit(100.0);
        henry.getAccount(Account.SAVINGS).deposit(4000.0);
        henry.getAccount(Account.SAVINGS).withdraw(200.0);
        henry.getAccount(Account.CHECKING).withdraw(50.0);

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
     * Test getStatementForAccount of Customer.java
     */ 
     @Test
     public void testStatementForAcount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.getAccount(Account.SAVINGS).deposit(4000.00);
        oscar.getAccount(Account.SAVINGS).withdraw(400.00);
        assertEquals(
              "Savings Account\n" +
              "  deposit $4,000.00\n" +
              "  withdrawal $400.00\n" +
              "Total $3,600.00", oscar.getStatementForAccount(Account.SAVINGS));     
     }

    /**
     * Test getNumberOfAccounts method of Customer.java.
     */
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * Test getNumberOfAccounts method of Customer.java.
     */
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    /**
     * Test getNumberOfAccounts method of Customer.java.
     */
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    /**
     * Test getName of Customer.java.
     */    
    @Test
    public void testName(){
        Customer andreas = new Customer("Andreas");
        assertEquals("Andreas", andreas.getName());
    }
    
    /**
     * Test openAccount method of Customer.java
     */
    @Test (expected = IllegalArgumentException.class)
     public void testOpenSameTypeAcounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.SAVINGS));
        assertEquals(2, oscar.getNumberOfAccounts());
    } 
     
     /**
      * Transfer between accounts.
      */
    @Test
     public void testTransferBetweenAccounts() {
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.getAccount(Account.CHECKING).deposit(1000.0);
        
        oscar.transferBetweenAccounts(Account.CHECKING, Account.SAVINGS, 500.0);
        
        assertEquals(500, oscar.getAccount(Account.SAVINGS).sumTransactions(),DOUBLE_DELTA);
        assertEquals(500, oscar.getAccount(Account.CHECKING).sumTransactions(),DOUBLE_DELTA);
    } 
     

}
