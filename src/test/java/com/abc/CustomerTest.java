package com.abc;

import main.java.com.abc.Account;
import main.java.com.abc.CheckingsAccount;
import main.java.com.abc.Customer;
import main.java.com.abc.SavingsAccount;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new CheckingsAccount();
        Account savingsAccount = new SavingsAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checkings Account" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account" +
                "  deposit $4,000.00\n" +
                "  withdrawal $-200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingsAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new SavingsAccount());
        oscar.openAccount(new CheckingsAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    /**
     * Testing if money transferring accounts works.
     */
    @Test
    public void testTransferAccount(){
    	Account sAccount = new SavingsAccount();
    	Account cAccount = new CheckingsAccount();
    	sAccount.deposit(100);
        Customer oscar = new Customer("Oscar");
        
        oscar.openAccount(sAccount);
        oscar.openAccount(cAccount);
        oscar.transferAccount(sAccount, cAccount, 100);
        assertEquals(100, cAccount.getBalance(), 0.001);
    }
}
