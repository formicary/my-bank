package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

	
	@Test
	public void testOpenAccount() {
		Account testAccount = new Account(Account.SAVINGS);
		Customer oscar = new Customer("Oscar")
        		.openAccount(testAccount);
		
		assertTrue("Oscars account list contains the testAccount"
				, oscar.getAccounts().contains(testAccount));
	}
	
	@Test
	public void testTransferFundsLeaveAccount() {
		int testDepositAmount = 100;
		int testTransferAmount = 20;
		
		Account testAccountTransferFrom = new Account(Account.SAVINGS);
		Account testAccountTransferTo = new Account(Account.CHECKING);
		
		Customer oscar = new Customer("Oscar")
        		.openAccount(testAccountTransferFrom).openAccount(testAccountTransferTo);
		
		testAccountTransferFrom.deposit(testDepositAmount);
		
		oscar.transferFunds(testAccountTransferFrom, testAccountTransferTo, testTransferAmount);
		
		assertEquals("Transfers 20 from savings to checking, savings account must equal 100 - 20"
				,testAccountTransferFrom.sumTransactions(), testDepositAmount - testTransferAmount, DOUBLE_DELTA);
	}
	
	@Test
	public void testTransferFundsReachAccount() {
		int testDepositAmount = 100;
		int testTransferAmount = 20;
		
		Account testAccountTransferFrom = new Account(Account.SAVINGS);
		Account testAccountTransferTo = new Account(Account.CHECKING);
		
		Customer oscar = new Customer("Oscar")
				.openAccount(testAccountTransferFrom).openAccount(testAccountTransferTo);
		
		testAccountTransferFrom.deposit(testDepositAmount);
		
		oscar.transferFunds(testAccountTransferFrom, testAccountTransferTo, testTransferAmount);
		
		assertEquals("Transfers 20 from savings to checking, checking account 20"
				,testAccountTransferTo.sumTransactions(), testTransferAmount, DOUBLE_DELTA);
	}

    @Test //Test customer statement generation
    public void testGetStatement(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement must match the following format for above transactions"
        		,"Statement for Henry\n" +
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

    @Test
    public void testGetNumberOfAccounts_OneAccount(){
        Customer oscar = new Customer("Oscar")
        		.openAccount(new Account(Account.SAVINGS));
        
        assertEquals("One account has been open so number of accounts must be 1"
        		,1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testGetNumberOfAccounts_ThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));;
        
        assertEquals("Three accounts have been opened so number of accounts must equal 3"
        		,3, oscar.getNumberOfAccounts());
    }
    

}
