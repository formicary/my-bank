package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class CustomerTest {

	@Test
    	protectec void testObj(){
		Account checkingAccount = new Account(Account.CHECKING);
		Account savingsAccount = new Account(Account.SAVINGS);
		Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        	Customer sarah = new Customer("Sarah").openAccount(checkingAccount).openAccount(savingsAccount);
        	
        	assertNotNull(henry);
		assertNotSame(henry, sarah);
	}
	
    	@Test 
   	protected void testApp(){
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
        	assertFalse("Incomplete statement" == henry.getStatement());
    	}

    	@Test
    	protected void testOneAccount(){
        	Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        	assertEquals(1, oscar.getNumberOfAccounts());
        	assertFalse(2 == oscar.getNumberOfAccounts());
   	}

    	@Test
    	protected void testTwoAccount(){
        	Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        	oscar.openAccount(new Account(Account.CHECKING));
        	assertEquals(2, oscar.getNumberOfAccounts());
        	assertFalse(3 == oscar.getNumberOfAccounts());
    	}

    	@Ignore
    	protected void testThreeAcounts() {
        	Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        	oscar.openAccount(new Account(Account.CHECKING));
        	assertEquals(3, oscar.getNumberOfAccounts());
    	}

}
