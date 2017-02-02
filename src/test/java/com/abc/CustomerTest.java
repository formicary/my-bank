package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	
	private Customer oscar;
	
	/**
	 * Test customer statement generation
	 */
	@Test
	public void testCustomerStatement() {
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        oscar = new Customer("Oscar");
        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        String statementExpected = "Statement for Oscar\n" +
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
                "Total In All Accounts $3,900.00";

        assertEquals(statementExpected, oscar.getStatement());
	}
	
	/**
	 * Test opening one customer account
	 */
    @Test
    public void testOpenOneAccount() {
        oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    /**
     * Test opening two customer accounts
     */
    @Test
    public void testOpenTwoAccounts(){
        oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    /**
     * Test opening three customer accounts
     * TODO creation of a third type of bank account
     */
    @Ignore
    public void testOpenThreeAcounts() {
        oscar = new Customer("Oscar");
        oscar.openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
