package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	 private static final double DOUBLE_DELTA = 1e-15;
	
      Customer oscar = new Customer("Oscar");
	  CheckingAccount checkingAccount = new CheckingAccount();
      SavingsAccount savingsAccount = new SavingsAccount();
      MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
      
      
    @Test 
    public void testApp(){
    	
        oscar.openAccount(checkingAccount).openAccount(savingsAccount);
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Oscar\n" +
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
                "Total In All Accounts $3,900.00", oscar.getStatement());
    }
    
    @Test
    public void totalInterestEarnedTest() {
    	
    	oscar.openAccount(checkingAccount);
    	checkingAccount.deposit(50);
    	checkingAccount.interestEarned();
    	assertEquals(0.05, oscar.totalInterestEarned(), DOUBLE_DELTA);
    	
    	oscar.openAccount(savingsAccount);
    	savingsAccount.deposit(50);
    	savingsAccount.interestEarned();
    	assertEquals(0.1, oscar.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testMaxiAccountStatment() {
    	
    }

    @Test
    public void testOneAccount(){
        oscar.openAccount(savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
       
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
    	
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
	@Test
	public void accountTransferTest1() {
		oscar.openAccount(savingsAccount).openAccount(checkingAccount);
	    savingsAccount.deposit(100);
	    checkingAccount.deposit(50);
	    oscar.accountTransfer(savingsAccount,checkingAccount, 50);
		assertEquals(100, checkingAccount.sumTransactions(), DOUBLE_DELTA);
		assertEquals(50, savingsAccount.sumTransactions(), DOUBLE_DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void accountTransferTest2() {
		
		oscar.accountTransfer(savingsAccount, checkingAccount, -50);
	}
}
