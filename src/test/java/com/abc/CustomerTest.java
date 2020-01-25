package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	 private static final double DOUBLE_DELTA = 1e-15;
	 
    @Test //Test customer statement generation
    public void testCustomerStatement(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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

    //test customer can have one type of account
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }
    
   //test customer can have two different types of accounts
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

  //test customer can have three different types of accounts
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    //test customer can transfer within their own accounts
    @Test
    public void testTransfer() {
    	Account savingAccount=new Account(Account.SAVINGS);
    	savingAccount.deposit(1000);
    	
    	Account checkingAccount=new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar")
                .openAccount(savingAccount);
        oscar.openAccount(checkingAccount);
        
        oscar.transfer(savingAccount, checkingAccount, 500);
        
        assertEquals(500, checkingAccount.sumTransactions(),DOUBLE_DELTA);
    }
    
   //test customer cannot transfer between accounts that aren't theirs
    @Test
    public void testFailedTransfer() {
    	Account savingAccount=new Account(Account.SAVINGS);
    	savingAccount.deposit(1000);
    	
    	Account checkingAccount=new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar")
                .openAccount(savingAccount);
        try {
        	oscar.transfer(savingAccount, checkingAccount, 500);
        }catch(IllegalArgumentException e){
        	//line 83 throws exception as oscar doesn't own checkingAccount
        	assertEquals(1000, savingAccount.sumTransactions(),DOUBLE_DELTA);
        }
    }
    
    //test interested is calculated properly
    @Test
    public void testTotalInterestEarned() {
    	Account checkingAccount=new Account(Account.CHECKING);
    	Account savingsAccount=new Account(Account.SAVINGS);
    	Account maxiAccount=new Account(Account.MAXI_SAVINGS);
    	
    	checkingAccount.deposit(1000);//earn $1 interest
    	savingsAccount.deposit(2000);//earn 3
    	maxiAccount.deposit(1000);//earn 50
    	
    	double total=checkingAccount.interestEarned()
    			+savingsAccount.interestEarned()+maxiAccount.interestEarned();
    	
    	assertEquals(54.0,total,DOUBLE_DELTA);
    }
}
