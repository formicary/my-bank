package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;
	Account checkingAccount, savingsAccount;
	Customer henry, oscar;
	
	@BeforeEach
	void setUp() throws Exception {
		checkingAccount = new Account(AccountType.CHECKING);
		savingsAccount = new Account(AccountType.SAVINGS);
		
		henry = new Customer("Henry");
		oscar = new Customer("Oscar");
	}

	/**
	 * Test customer statement generation
	 */
	@Test 
    public void testApp(){

        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

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

	/**
	 * 
	 */
    @Test
    public void testOneAccount(){
        oscar.openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * 
     */
    @Test
    public void testTwoAccount(){
        oscar.openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    /**
     * test that the transferBetweenAccounts method accurately moves money 
     * from one account to another
     */
    @Test
    public void testTransferBetweenAccounts() {
    	henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
    	henry.transferBetweenAccounts(savingsAccount, checkingAccount, 500);
    	assertEquals(3300, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(600, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }

}
