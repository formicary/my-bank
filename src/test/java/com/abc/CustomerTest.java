package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testStatementWhenAccountsAreValid() {    	
    	//Arrange
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        
        Customer henry = new Customer("Henry")
        		.openAccount(checkingAccount)
        		.openAccount(savingsAccount);
        
        //Act
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        //Assert
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
    
    @Test
    public void testInvalidAccountThrowsException() {    	
    	//Arrange
		try {
			Account invalidAccount = new Account(3);
			fail();
		}
		catch (IllegalArgumentException e) {
			//Assert
			assertEquals("Invalid account type", e.getMessage());
		}
    }
    
    @Test
    public void testCustomerCanOpenAccount() {
    	//Arrange
    	Customer george = new Customer("George");
    	
    	//Act
    	george.openAccount(new Account(Account.CHECKING));
    	
    	//Assert
    	assertEquals(1, george.getNumberOfAccounts());
    }
    
    @Test
    public void testCustomerCanDepositFunds() {
    	//Arrange
    	Account checkingAccount = new Account(Account.CHECKING);
    	
    	//Act
    	checkingAccount.deposit(50.0);
    	
    	//Assert
    	assertEquals(50.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void testCustomerCanWithdrawFunds() {
    	//Arrange
    	Account checkingAccount = new Account(Account.CHECKING);
    	
    	//Act
    	checkingAccount.withdraw(50.0);
    	
    	//Assert
    	assertEquals(-50.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
   

    @Test
    public void testOneAccount() {
    	//Arrange
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        
        //Assert
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() {
        //Arrange
    	Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.CHECKING))
                .openAccount(new Account(Account.SAVINGS));
        
        //Assert
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
    	//Arrange
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.CHECKING))
                .openAccount(new Account(Account.SAVINGS))
                .openAccount(new Account(Account.MAXI_SAVINGS));
        
        //Assert
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
