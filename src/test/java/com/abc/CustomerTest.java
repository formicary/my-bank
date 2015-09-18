package com.abc;

import junit.framework.TestCase;

import org.junit.Test;

import com.abc.Account.AccountType;

public class CustomerTest extends TestCase {
	
	@Test
    public void testNullAsName(){
		try {
			Customer henry = new Customer(null);
			henry.getStatement();
			fail("Should throw IllegalArgumentException when name is null");
		} catch (IllegalArgumentException e) {
			assert(e.getMessage().contains("name must be specified"));
		}
	}
	
	@Test
    public void testEmptyStringAsName(){
		try {
			Customer henry = new Customer("");
			henry.getStatement();
			fail("Should throw IllegalArgumentException when name is an empty string");
		} catch (IllegalArgumentException e) {
			assert(e.getMessage().contains("name must be specified"));
		}
	}

    @Test
    public void testStatementCreditBalance(){
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

    @Test
    public void testStatementDebitBalance(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        checkingAccount.withdraw(200.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(8000.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  withdrawal $200.00\n" +
                "Total -$100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $8,000.00\n" +
                "Total -$4,000.00\n" +
                "\n" +
                "Total In All Accounts -$4,100.00", henry.getStatement());
    }
    
    @Test
    public void testStatementTransfer(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        henry.transfer(savingsAccount, checkingAccount, 1000);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "  deposit $1,000.00\n" +
                "Total $1,100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $1,000.00\n" +
                "Total $3,000.00\n" +
                "\n" +
                "Total In All Accounts $4,100.00", henry.getStatement());
    }

    @Test
    public void testOneAccountCustomer(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccountsCustomer(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcountsCustomer() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
