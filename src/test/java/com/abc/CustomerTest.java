package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CustomerTest {

	private static final String ACCOUNT_NUMBER_1 = "ABC";
	private static final String ACCOUNT_NUMBER_2 = "DEF";
	private static final String ACCOUNT_NUMBER_3 = "GHI";
	
	@Test
	public void customerReturnsName()
	{
		//Given
		final String expected = "Hello";
		final List<Account> accounts = new ArrayList<Account>();
		
		//When
		final Customer customer = new Customer(expected, accounts);
		final String actual = customer.getName();
		
		//Then
		assertEquals(expected, actual);
	}
	
	@Test
	public void customerReturnsNumberOfAccounts()
	{
		//Given
		final String ignore = "Hello";
		final List<Account> accounts = new ArrayList<Account>();
		accounts.add(AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_1));
		final int expected = 1;
		
		//When
		final Customer customer = new Customer(ignore, accounts);
		final int actual = customer.getNumberOfAccounts();
		
		//Then
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void customerNoAccountsReturnsZeroTotalInterestEarned()
	{
		//Given
		final String ignore = "Hello";
		final List<Account> accounts = new ArrayList<Account>();
		final double expected = 0;
		
		//When
		final Customer customer = new Customer(ignore, accounts);
		final double actual = customer.totalInterestEarned();
		
		//Then
		assertEquals(expected, actual, 0.0d);
	}
	
	
	
    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1);
        Account savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_2);
        final List<Account> accounts = new ArrayList<Account>();
        accounts.add(checkingAccount);
        accounts.add(savingsAccount);
        
        Customer henry = new Customer("Henry", accounts);

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
    	Account savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_1);
    	final List<Account> accounts = new ArrayList<Account>();
        accounts.add(savingsAccount);
    	Customer oscar = new Customer("Oscar", accounts);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
    	Account checkingAccount = AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1);
        Account savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_2);
        final List<Account> accounts = new ArrayList<Account>();
        accounts.add(checkingAccount);
        accounts.add(savingsAccount);
    	Customer oscar = new Customer("Oscar", accounts);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
    	Account checkingAccount = AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1);
        Account savingsAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_2);
        Account maxiSavingsAccount = AccountsTestHelper.createMaxiSavingsAccount(ACCOUNT_NUMBER_3);
        
        final List<Account> accounts = new ArrayList<Account>();
        accounts.add(checkingAccount);
        accounts.add(savingsAccount);
        accounts.add(maxiSavingsAccount);
    	Customer oscar = new Customer("Oscar", accounts);
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
