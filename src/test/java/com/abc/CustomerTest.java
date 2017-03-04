package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.*;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Customer oscar;
    private CheckingAccount checkingAccount;
    private SavingsAccount savingsAccount;
    private MaxiSavingsAccount maxiSavingsAccount;
    private Bank bank;
    
	@Before
	public void setUp() {
		bank = new Bank();
		oscar = new Customer("Oscar");
		checkingAccount = new CheckingAccount();
	    savingsAccount = new SavingsAccount();	
	    maxiSavingsAccount = new MaxiSavingsAccount();
	}

    @Test //Test customer statement generation
    public void testApp(){
    	


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
        oscar.openAccount(savingsAccount);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        oscar.openAccount(savingsAccount);
        oscar.openAccount(checkingAccount);
        oscar.openAccount(maxiSavingsAccount);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
