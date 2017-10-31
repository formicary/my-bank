package com.abc;

//Imports
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CustomerTest {
	//Constant for assertEquals method used on double data types
	private static final double DOUBLE_DELTA = 1e-15;

	//Test to assert that a customer bank statement produces the correct string when the customer has a number of transactions over two accounts
    @Test
    public void testApp(){

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

    //(I see no point in testing getter methods)
    
    //Test to assert that a transfer between a customer's accounts returns the correct end amount for both of their respective accounts involved in the transferral
    @Test
    public void testTransfer() {
    	Customer oscar = new Customer("Oscar");
    	Account savingsAccount = new Account(Account.SAVINGS);
    	Account checkingAccount = new Account(Account.CHECKING);
    	
    	savingsAccount.deposit(3000.0);
    	checkingAccount.deposit(2000.0);
    	
    	oscar.openAccount(savingsAccount);
    	oscar.openAccount(checkingAccount);
    	
    	oscar.transferBetweenAccounts(savingsAccount, checkingAccount, 1000.0);
    	
    	assertEquals(2000.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(3000.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
