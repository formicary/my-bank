package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();

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
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCustomerNameNumbers() {
    	Customer test = new Customer("1243532");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCustomerNameSymbols() {
    	Customer test = new Customer("&*%&*£%£");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCustomerNameLength() {
    	Customer test = new Customer("");
    }
    
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Savings());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Checking());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Savings());
        oscar.openAccount(new Checking());
        oscar.openAccount(new MaxiSavings());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testValidTransfer() {
    	Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();
        
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(checkingAccount);
    	oscar.openAccount(savingsAccount);
    	checkingAccount.deposit(200);
    	oscar.transfer(1, 2, 100);
    	assertEquals(100,checkingAccount.getBalance(),DOUBLE_DELTA);
    	assertEquals(100,savingsAccount.getBalance(),DOUBLE_DELTA);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testInvalidTransfer() {
    	Account checkingAccount = new Checking();
        Account savingsAccount = new Savings();
        
    	Customer oscar = new Customer("Oscar");
    	oscar.openAccount(checkingAccount);
    	oscar.openAccount(savingsAccount);
    	checkingAccount.deposit(200);
    	oscar.transfer(1, 2, 300);
    }
}
