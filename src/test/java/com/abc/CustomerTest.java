package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testGetName() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals("Oscar", oscar.getName());
    }
    
    @Test // Test customer statement generation
    public void testApp() {

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        Account maxiAccount = new Account(Account.MAXI_SAVINGS);
        
        Customer henry = new Customer("Henry").openAccount(checkingAccount)
                 .openAccount(savingsAccount).openAccount(maxiAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiAccount.deposit(245.60);

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
                "Maxi Savings Account\n" +
                "  deposit $245.60\n" +
                "Total $245.60\n" +
                "\n" +
                "Total In All Accounts $4,145.60", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testInterestEarned() {
        Account checkingAccount = new Account(Account.CHECKING);
        Customer oscar = new Customer("Oscar").openAccount(checkingAccount); 
        checkingAccount.deposit(3000.0);
        
        assertEquals(3.0, oscar.totalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
