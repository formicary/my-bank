package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	
	public void checkName() {
        Customer henry = new Customer("Henry");
        assertEquals(henry.getName(), "Henry");
	}

    @Test //Test customer statement generation
    public void checkStatement(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(300);
        maxiSavingsAccount.transfer(100, checkingAccount);
        
        String statement = "Statement for Henry\n" + 
        		"\n" + 
        		"Account Type: CHECKING\n" + 
        		"DEPOSIT: 100.0\n" + 
        		"RECEIVE: 100.0 from: MAXI_SAVINGS\n" + 
        		"Current balance: $200.00\n" + 
        		"\n" + 
        		"Account Type: SAVINGS\n" + 
        		"DEPOSIT: 4000.0\n" + 
        		"WITHDRAW: 200.0\n" + 
        		"Current balance: $3,800.00\n" + 
        		"\n" + 
        		"Account Type: MAXI_SAVINGS\n" + 
        		"DEPOSIT: 300.0\n" + 
        		"TRANSFER: 100.0 to: CHECKING\n" + 
        		"Current balance: $200.00\n" + 
        		"\n" + 
        		"Total In All Accounts $4,200.00";

        assertEquals(statement, henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new CheckingAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar").openAccount(new CheckingAccount()).openAccount(new SavingsAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testThreeAccounts(){
        Customer oscar = new Customer("Oscar").openAccount(new CheckingAccount()).openAccount(new SavingsAccount()).openAccount(new MaxiSavingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void checkTotalInterestEarned() {
        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);
        
        checkingAccount.deposit(1000);
        savingsAccount.deposit(700);
        maxiSavingsAccount.deposit(300);
        
        double totalInterest = 0;
        
        checkingAccount.gainInterest();
        savingsAccount.gainInterest();
        maxiSavingsAccount.gainInterest();
        
        // Have tested separate account interest so can use this
        totalInterest += checkingAccount.getTotalInterestEarned();
        totalInterest += savingsAccount.getTotalInterestEarned();
        totalInterest += maxiSavingsAccount.getTotalInterestEarned();

        assertEquals(totalInterest, henry.getTotalInterestEarned(), DOUBLE_DELTA);
    }
}
