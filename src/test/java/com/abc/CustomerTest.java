package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;
	
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
    public void testTranfer(){
    	Account checking = new Account(Account.CHECKING);
    	Account savings = new Account(Account.SAVINGS);
    	Account maxi_savings = new Account(Account.MAXI_SAVINGS);
        Customer oscar = new Customer("Oscar");
        
        oscar.openAccount(checking);
        oscar.openAccount(savings);
        oscar.openAccount(maxi_savings);
        
        checking.deposit(300.0);
        savings.deposit(2000.0);
        maxi_savings.deposit(3500.0);
        
        oscar.transfer(0, 1, 300.0);
        assertEquals(0.0, checking.getMoney(), DOUBLE_DELTA);
        assertEquals(2300.0, savings.getMoney(), DOUBLE_DELTA);
        
        
        oscar.transfer(1, 2, 500.0);
        assertEquals(1800.0, savings.getMoney(), DOUBLE_DELTA);
        assertEquals(4000.0, maxi_savings.getMoney(), DOUBLE_DELTA);
        
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

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
