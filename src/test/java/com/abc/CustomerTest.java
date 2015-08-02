package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	/*
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
    */
	
	@Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        henry.openAccount(savingsAccount);
        
        henry.deposit(checkingAccount, 100.0);
        henry.deposit(savingsAccount, 4000.0);
        savingsAccount.setLastAccruementDate(2);
        checkingAccount.setLastAccruementDate(2);
        henry.withdraw(savingsAccount, 200.0);
        henry.transfer(savingsAccount, checkingAccount, 500);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  interest $0.10\n" +
                "  interest $0.10\n" +
                "  deposit $500.00\n" +
                "Total $600.20\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  interest $7.00\n" +
                "  interest $7.01\n" +
                "  withdrawal $200.00\n" +
                "  withdrawal $500.00\n" +
                "Total $3,314.01\n" +
                "\n" +
                "Total In All Accounts $3,914.21", henry.getStatement());
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
    public void accureInterest(){

        Account checkingAccount = new Account(Account.CHECKING);

        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        
        henry.deposit(checkingAccount, 1000.0);
        checkingAccount.setLastAccruementDate(10);
        henry.deposit(checkingAccount, 100.0);
        assertEquals(1110.05, (double)Math.round(checkingAccount.sumTransactions()*100)/100, DOUBLE_DELTA);
    }
}
