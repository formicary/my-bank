package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;

public class CustomerTest {
	
	Customer testCustomer;
    CheckingAccount checkingAccount;
    SavingsAccount savingsAccount;
    MaxiSavingsAccount maxiSavingsAccount;
    private static final double delta = 1e-15;
	
	@Before
	public void setUpBeforeEach() {
		testCustomer = new Customer("John");
        
        checkingAccount = new CheckingAccount();
        savingsAccount = new SavingsAccount();
        maxiSavingsAccount = new MaxiSavingsAccount();
        
        testCustomer.openAccount(checkingAccount);
        testCustomer.openAccount(savingsAccount);
        testCustomer.openAccount(maxiSavingsAccount);
	}

    @Test
    public void statementTest(){

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(1000.0);
        savingsAccount.withdraw(500.0);
        maxiSavingsAccount.deposit(1000);
        maxiSavingsAccount.withdraw(1000);

        String expStr = "Statement for John\n" +
        		"\n" +
        		"Checking Account\n" + 
        		"\n" + 
        		"  Deposit $1000.00\n" + 
        		"Total: $1000.00\n" + 
        		"\n" + 
        		"Savings Account\n" + 
        		"\n" + 
        		"  Deposit $1000.00\n" + 
        		"  Withdrawal $500.00\n" + 
        		"Total: $500.00\n" + 
        		"\n" + 
        		"Maxi-Savings Account\n" + 
        		"\n" + 
        		"  Deposit $1000.00\n" + 
        		"  Withdrawal $1000.00\n" + 
        		"Total: $0.00\n" + 
        		"\n" + 
        		"Total for all accounts: $1500.00";

        
        assertEquals(expStr, testCustomer.getStatement());
    }
    
    @Test
    public void interestTest() {
    	checkingAccount.deposit(1000);
    	savingsAccount.deposit(2000);
    	maxiSavingsAccount.deposit(3000);
    	assertEquals(154, testCustomer.totalInterestEarned(), delta);
    }
    
    @Test
    public void zeroTransferTest() {
        checkingAccount.deposit(1000.0);
		try {
			testCustomer.transfer(checkingAccount, savingsAccount, 0);;
			fail("Cannot have a zero transfer");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Amount must be greater than zero", e.getMessage());
		}
    }
    
    @Test
    public void negativeTransferTest() {
        checkingAccount.deposit(1000.0);
		try {
			testCustomer.transfer(checkingAccount, savingsAccount, -1);;
			fail("Cannot have a negative transfer");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Amount must be greater than zero", e.getMessage());
		}
    }
    
    @Test
    public void transferTest() {
        checkingAccount.deposit(1000.0);
        testCustomer.transfer(checkingAccount, savingsAccount, 500);
        
        String expStr = "Statement for John\n" +
        		"\n" +
        		"Checking Account\n" + 
        		"\n" + 
        		"  Deposit $1000.00\n" + 
        		"  Withdrawal $500.00\n" +
        		"Total: $500.00\n" + 
        		"\n" + 
        		"Savings Account\n" + 
        		"\n" + 
        		"  Deposit $500.00\n" + 
        		"Total: $500.00\n" + 
        		"\n" + 
        		"Maxi-Savings Account\n" + 
        		"\n" + 
        		"Total: $0.00\n" + 
        		"\n" + 
        		"Total for all accounts: $1000.00";

        assertEquals(expStr, testCustomer.getStatement());
		
    }
}
