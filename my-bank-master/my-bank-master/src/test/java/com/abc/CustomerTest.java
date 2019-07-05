package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        //Create a customer and open two accounts
        Customer henry = new Customer("Henry");
        henry.openAccount(checkingAccount);
        henry.openAccount(savingsAccount);
        
        //Test transaction types
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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        Account oscarsSavings = new Account(Account.SAVINGS);
        oscar.openAccount(oscarsSavings);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        Account oscarsSavings = new Account(Account.SAVINGS);
        oscar.openAccount(oscarsSavings);
        Account oscarsCheck = new Account(Account.CHECKING);
        oscar.openAccount(oscarsCheck);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");
        Account oscarsSavings = new Account(Account.SAVINGS);
        oscar.openAccount(oscarsSavings);
        Account oscarsCheck = new Account(Account.CHECKING);
        oscar.openAccount(oscarsCheck);
        Account oscarsMax = new Account(Account.MAXI_SAVINGS);
        oscar.openAccount(oscarsMax);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test 
    //Test the transaction method works as expected
    public void testMove() {
    	Customer rick = new Customer("Rick");
    	Account ricksChecking = new Account(Account.CHECKING);
    	rick.openAccount(ricksChecking);
    	Account ricksSavings = new Account(Account.SAVINGS);
    	rick.openAccount(ricksSavings);
    	rick.moveMoney(ricksChecking, ricksSavings, 30);
    	assertEquals("Statement for Rick\n" +
                "\n" +
                "Checking Account\n" +
                "  withdrawal $30.00\n" +
                "Total -$30.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $30.00\n" +
                "Total $30.00\n"+
                "\n" +
                "Total In All Accounts $0.00", rick.getStatement());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void stealMoney() {
    	//Secondary test, ensure can't transfer to accounts not belong to the customer
    	Customer rick = new Customer("Rick");
    	Account ricksChecking = new Account(Account.CHECKING);
    	rick.openAccount(ricksChecking);
    	Customer thief = new Customer("Thief");
    	Account thiefAccount = new Account(Account.CHECKING);
    	thief.openAccount(thiefAccount);
    	rick.moveMoney(ricksChecking, thiefAccount, 1);
    	thief.moveMoney(thiefAccount, ricksChecking, 2);
    	System.out.println(thief.getStatement());
    	assertEquals("Statement for Thief\n" +
                "\n" +
                "Checking Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $0.00", thief.getStatement());
    }
    
    @Test
    //Testing that negative account balances are displayed properly
    public void testNegetive() {
    	Customer toby = new Customer("Toby");
    	Account tobyCheck = new Account(Account.CHECKING);
    	toby.openAccount(tobyCheck);
    	tobyCheck.withdraw(30);
    	assertEquals("Statement for Toby\n" +
                "\n" +
                "Checking Account\n" +
                "  withdrawal $30.00\n" +
                "Total -$30.00\n" +
                "\n" +
                "Total In All Accounts -$30.00", toby.getStatement());
    }
}
