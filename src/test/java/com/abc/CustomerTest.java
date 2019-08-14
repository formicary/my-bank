package com.abc;

import org.junit.Ignore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(new Money("100.0"));
        savingsAccount.deposit(new Money("4000.0"));
        savingsAccount.withdraw(new Money("200.0"));
        
        assertEquals("Statement for Henry\n\n" +
        			"CHECKING  deposit 100.00\n" +
        			"Total 100.00\n\n" +
        			"SAVINGS  deposit 4000.00\n" +
       				"  withdrawal -200.00\n" +
        			"Total 3800.00\n\n" +
        			"Total In All Accounts 3900.00", henry.getStatement());
    }
    
    

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void totalInterestEarned(){
    	Customer c = new Customer("Tom Sturgeon");
    	Account checking = new CheckingAccount();
    	Account savings = new SavingsAccount();
    	
    	checking.deposit(new Money("1000")); // 0.1 flat rate
    	savings.deposit(new Money("1100")); // 0.2 interest
    	
    	c.openAccount(checking);
    	c.openAccount(savings);
    	
    	assertEquals(new Money("3.2").getAmount(), c.totalInterestEarned().getAmount());
    	
    }
}
