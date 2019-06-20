package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
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
    
    @Test
    public void testCustomerCanOpenAccount() {
    	//Arrange
    	Customer george = new Customer("George");
    	
    	//Act
    	george.openAccount(new Account(Account.CHECKING));
    	
    	//Assert
    	assertEquals(1, george.getNumberOfAccounts());
    }
    
    @Test
    public void testCustomerCanDepositFunds() {
    	//Arrange
    	Account checkingAccount = new Account(Account.CHECKING);
    	Customer george = new Customer("George").openAccount(checkingAccount);
    	
    	//Act
    	checkingAccount.deposit(50.0);
    	
    	//Assert
    	assertEquals(50.0, checkingAccount.sumTransactions(), 0.001);
    }
    
    @Test
    public void testCustomerCanWithdrawFunds() {
    	//Arrange
    	Account checkingAccount = new Account(Account.CHECKING);
    	Customer george = new Customer("George").openAccount(checkingAccount);
    	
    	//Act
    	checkingAccount.withdraw(50.0);
    	
    	//Assert
    	assertEquals(-50.0, checkingAccount.sumTransactions(), 0.001);
    }
   

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAccounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
