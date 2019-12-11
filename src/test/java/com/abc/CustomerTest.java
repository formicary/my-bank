package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new AccountFactory(AccountFactory.CHECKING).getAccount();
        Account savingsAccount = new AccountFactory(AccountFactory.SAVINGS).getAccount();

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
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new AccountFactory(AccountFactory.SAVINGS).getAccount());
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new AccountFactory(AccountFactory.SAVINGS).getAccount());
        oscar.openAccount(new AccountFactory(AccountFactory.CHECKING).getAccount());
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new AccountFactory(AccountFactory.SAVINGS).getAccount());
        oscar.openAccount(new AccountFactory(AccountFactory.CHECKING).getAccount());
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testswitchingAccount(){
    	Account a = new AccountFactory(AccountFactory.SAVINGS).getAccount();
    	
        Customer oscar = new Customer("Oscar")
                .openAccount(a);
        
        a = oscar.switchAccounts(a, AccountFactory.MAXI_SAVINGS);
        
        assertEquals(AccountFactory.MAXI_SAVINGS, a.getAccountType());
    }
}
