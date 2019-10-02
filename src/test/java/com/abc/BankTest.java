package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class BankTest {
	
	Bank testBank;
	Customer testCustomer1;
	Customer testCustomer2;
	CheckingAccount checkingAccount1;
	CheckingAccount checkingAccount2;
	SavingsAccount savingsAccount;
    MaxiSavingsAccount maxiSavingsAccount;
    private static final double delta = 1e-15;
    
	@Before
	public void setUpBeforeEach() {
		testBank = new Bank();
		
		testCustomer1 = new Customer("John");
        testCustomer2 = new Customer("Emma");
                
        testCustomer1.openAccount(checkingAccount1 = new CheckingAccount());
        testCustomer2.openAccount(checkingAccount2 = new CheckingAccount());
        testCustomer2.openAccount(savingsAccount = new SavingsAccount());
        testCustomer2.openAccount(maxiSavingsAccount =  new MaxiSavingsAccount());
        
        testBank.addCustomer(testCustomer1);
        testBank.addCustomer(testCustomer2);
	}
    
    @Test
    public void customerSummaryTest() {
        
        String expStr = "Customer Summary\n" + 
        		"\n" + 
        		"John: 1 account\n" + 
        		"Emma: 3 accounts";
        assertEquals(expStr, testBank.customerSummary());
    }
    
    @Test
    public void interestTest() {
    	checkingAccount1.deposit(1000);
    	checkingAccount2.deposit(2000);
    	savingsAccount.deposit(2000);
    	maxiSavingsAccount.deposit(3000);
    	assertEquals(156, testBank.totalInterestPaid(), delta);
    	SavingsAccount savingsAccount = new SavingsAccount();
    	testCustomer1.openAccount(savingsAccount);
    	
    	System.out.print(testCustomer1.totalInterestEarned());
    }
}
