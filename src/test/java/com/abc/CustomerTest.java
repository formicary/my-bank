package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = AccountFactory.getInstance().createAccount(Account.CHECKING);
        Account savingsAccount = AccountFactory.getInstance().createAccount(Account.SAVINGS);

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
    
    @Test
    public void testSavingsAccountCreation(){
        Customer oscar = new Customer("Oscar").openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
        assertEquals(Account.SAVINGS, oscar.getAccountByID(1).getAccountType());
    }
    
    @Test
    public void testCheckingAccountCreation(){
        Customer oscar = new Customer("Oscar").openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
        assertEquals(Account.CHECKING, oscar.getAccountByID(1).getAccountType());
    }
    
    @Test
    public void testMaxiAccountCreation(){
        Customer oscar = new Customer("Oscar").openAccount(AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS));
        assertEquals(Account.MAXI_SAVINGS, oscar.getAccountByID(1).getAccountType());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
        oscar.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
        oscar.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
        oscar.openAccount(AccountFactory.getInstance().createAccount (Account.MAXI_SAVINGS));
		assertEquals(3, oscar.getNumberOfAccounts());
	}
    
    @Test
    public void testMultipleSavingsAccounts() {
    	Customer oscar = new Customer("Oscar")
    			.openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
    	assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testMultipleCheckingAccounts() {
    	Customer oscar = new Customer("Oscar")
    			.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
    	assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testMultipleMaxiSavingsAccounts() {
    	Customer oscar = new Customer("Oscar")
    			.openAccount(AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS));
    	assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test 
    public void testMultipleAccounts() {
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.MAXI_SAVINGS));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
    	assertEquals(6, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testAccountTransfer() {
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING));
    	oscar.openAccount(AccountFactory.getInstance().createAccount(Account.SAVINGS));
    	oscar.getAccountByID(1).deposit(1000);
    	
    	assertEquals(1000, oscar.getAccountByID(1).getBalance(), DOUBLE_DELTA);
    	assertEquals(0, oscar.getAccountByID(2).getBalance(), DOUBLE_DELTA);
    	
    	oscar.transferBetweenAccounts(1, 2, 500);
    	
    	assertEquals(500, oscar.getAccountByID(1).getBalance(), DOUBLE_DELTA);
    	assertEquals(500, oscar.getAccountByID(2).getBalance(), DOUBLE_DELTA);
    }
    
    
}
