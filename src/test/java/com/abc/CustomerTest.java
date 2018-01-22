package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation 
    public void testApp() {

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

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
    
    @Test //Test for transfer between accounts
    public void testTransfer() {

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiSAccount = new Account(AccountType.MAXI_SAVINGS);

        // opening first account - Checking
        Customer tom = new Customer("Tom").openAccount(checkingAccount);

        checkingAccount.deposit(100.0);
        
        // not possible as only 1 account
        assertEquals(false, tom.transfer(30, checkingAccount, savingsAccount));      
        
        // opening second account - Saving
        tom.openAccount(savingsAccount);
        
        // returns false due to non existent account MAXI ACCOUNT.
        assertEquals(false, tom.transfer(30, checkingAccount, maxiSAccount));
        
        // opening third account - MAXI SAVINGS
        tom.openAccount(maxiSAccount);
        
        // returns false due to amount being more than balance in the account
        assertEquals(false, tom.transfer(100.1, checkingAccount, maxiSAccount));
        
        // pass
        assertEquals(true, tom.transfer(50, checkingAccount, savingsAccount));
        
        
        // transfer summary
        assertEquals("Statement for Tom\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $50.00\n" +
                "Total $50.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $100.00", tom.getStatement());
        
        
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testDeposit() {
    	Account savingsAccount = new Account(AccountType.SAVINGS);
    	Customer oscar = new Customer("Oscar").openAccount(savingsAccount);
    	
    	assertEquals(true, savingsAccount.deposit(100));
    	assertEquals("Statement for Oscar\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $100.00", oscar.getStatement());
    }
}
