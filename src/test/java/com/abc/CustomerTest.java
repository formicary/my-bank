package com.abc;

import main.java.com.abc.*;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){



        Customer henry = new Customer("Henry").openAccount(new Account(Account.CHECKING)).openAccount(new Account(Account.SAVINGS));
        
        Account checkingAccount= henry.getAccount(0);
        Account savingsAccount= henry.getAccount(1);
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
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    @Test
    public void testAccountTransfer(){
    	Account checkingAccount = new Account(Account.CHECKING, 0);
        Account checkingAccountTwo = new Account(Account.CHECKING, 1);
    	Customer oscar = new Customer("Oscar")
        	.openAccount(checkingAccount);
    	oscar.openAccount(checkingAccountTwo);
    	checkingAccount.deposit(150.0);
    	checkingAccountTwo.deposit(100.0);
    	double amountToTransfer = 150.0;
    	oscar.transferBetweenAccounts(checkingAccount, checkingAccountTwo, amountToTransfer);
    	assertEquals(0.0, checkingAccount.getBalance(),DOUBLE_DELTA);
    	assertEquals(250.0, checkingAccountTwo.getBalance(), DOUBLE_DELTA);

    }
    @Test(expected=IllegalArgumentException.class)
    public void testAccountTransferSingleAccount(){
    	Account checkingAccount = new Account(Account.CHECKING);
        Account checkingAccountTwo = new Account(Account.CHECKING);
    	Customer oscar = new Customer("Oscar")
        	.openAccount(checkingAccount);
    	oscar.openAccount(checkingAccountTwo);
    	checkingAccount.deposit(150.0);
    	checkingAccountTwo.deposit(100.0);
    	double amountToTransfer = 150;
    	//test for transfer between the same account
        oscar.transferBetweenAccounts(checkingAccount, checkingAccount, amountToTransfer);
    }
}
