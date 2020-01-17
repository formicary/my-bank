package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

       
        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals(
        		"Statement for Henry\n" +
        
        		
                "\n" +
                " Checking Account\n" +
                " Transaction : " +  "Deposit , amount = $100.00\n" 
                + "\n" +
                "Total $100.00\n" +
                "\n" +
                
                
                " Savings Account\n" +
                " Transaction : " +  "Deposit , amount = $4,000.00\n" +
                " Transaction : " +  "Withdrawal , amount = $200.00\n" + "\n" +
                
                "Total $3,800.00\n" +
                "\n" +
                
                
                "Total In All Accounts $3,900.00", 
                
                henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
   
	@Test
    
    public void checkTransfer() 	{
    	
    	Customer alice = new Customer("Alice");
    	
    	Account checkingAccount = new Account(Account.CHECKING);
    	Account maxi_savingsAccount = new Account(Account.MAXI_SAVINGS);
    	
    	alice.openAccount(checkingAccount);
    	alice.openAccount(maxi_savingsAccount);
    	
    	checkingAccount.deposit(1000);
    	maxi_savingsAccount.deposit(5000);
    	
    	alice.transfer(checkingAccount, maxi_savingsAccount, 500);
    	assertEquals(5500, maxi_savingsAccount.getBalance(), DOUBLE_DELTA);
    	   
    	
    }
	
@Test(expected=IllegalArgumentException.class)
    
    public void checInvalidTransfer() 	{
    	
    	Customer alice = new Customer("Alice");
    	
    	Account checkingAccount = new Account(Account.CHECKING);
    	Account maxi_savingsAccount = new Account(Account.MAXI_SAVINGS);
    	
    	alice.openAccount(checkingAccount);
    	alice.openAccount(maxi_savingsAccount);
    	
    	checkingAccount.deposit(1000);
    	maxi_savingsAccount.deposit(5000);
    	
    	alice.transfer(checkingAccount, maxi_savingsAccount, 1500);
    	assertEquals("You do not have enough funds to transfer", maxi_savingsAccount.getBalance());
    	   
    	
    }
}
