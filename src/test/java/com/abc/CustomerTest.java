package com.abc;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class CustomerTest {

	@Test
	public void customerReturnsName()
	{
		//Given
		final String expected = "Hello";
		
		//When
		final Customer customer = new Customer(expected);
		final String actual = customer.getName();
		
		//Then
		assertEquals(expected, actual);
	}
	
	@Test
	public void customerReturnsNumberOfAccounts()
	{
		//Given
		final String ignore = "Hello";
		final int expected = 1;
		
		//When
		final Customer customer = new Customer(ignore);
		customer.openAccount(new Account(0));
		final int actual = customer.getNumberOfAccounts();
		
		//Then
		assertEquals(expected, actual);
	}
	
	
	@Test
	public void customerNoAccountsReturnsZeroTotalInterestEarned()
	{
		//Given
		final String ignore = "Hello";
		final double expected = 0;
		
		//When
		final Customer customer = new Customer(ignore);
		final double actual = customer.totalInterestEarned();
		
		//Then
		assertEquals(expected, actual, 0.0d);
	}
	
	
	
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
}
