package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class BankTest {
    private static final String CUSTOMER_NAME = "Bill";
	private static final double DOUBLE_DELTA = 1e-15;
    private static final String ACCOUNT_NUMBER_1 = "ABC";
    private static final String ACCOUNT_NUMBER_2 = "DEF";
    
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        
        final Customer customer = bank.addCustomer("John");
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1));

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1);
        Customer customer = bank.addCustomer(CUSTOMER_NAME);
        bank.openAccount(customer.getName(), checkingAccount);
        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_1);
        Customer customer = bank.addCustomer(CUSTOMER_NAME);
        bank.openAccount(customer.getName(), checkingAccount);

        checkingAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = AccountsTestHelper.createMaxiSavingsAccount(ACCOUNT_NUMBER_1);
        Customer customer = bank.addCustomer(CUSTOMER_NAME);
        bank.openAccount(customer.getName(), checkingAccount);

        checkingAccount.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void transferBetweenAccounts() {
    	//Given
    	Bank bank = new Bank();
    	Account from = AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1);
		Account to = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_2);
		double amount = 100.00;		
		from.deposit(amount);
		
		//When
    	bank.transferBetweenAccounts(from, to, amount);
    	
    	//Then
    	assertEquals(amount, to.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(0.0, from.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test
    public void transferBetweenCustomerAccounts() {
    	//Given
    	Bank bank = new Bank();
    	Customer customer = bank.addCustomer(CUSTOMER_NAME);
    	Account from = AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1);
		Account to = AccountsTestHelper.createSavingsAccount(ACCOUNT_NUMBER_2);
		double amount = 100.00;		
		from.deposit(amount);
        bank.openAccount(customer.getName(), from);
        bank.openAccount(customer.getName(), to);
        
		//When
    	bank.transferBetweenCustomerAccounts(CUSTOMER_NAME, ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2, amount);
    	
    	//Then
    	assertEquals(amount, to.sumTransactions(), DOUBLE_DELTA);
    	assertEquals(0.0, from.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void transferBetweenCustomerAccountsThrowsIfCustomerDoesNotExist() {
    	//Given
    	final Bank bank = new Bank();
    	final Customer customer = bank.addCustomer(CUSTOMER_NAME);
		final double amount = 100.00;		
		final String noneCustomer = "John";
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1));
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_2));

        try {
        	//When
        	bank.transferBetweenCustomerAccounts(noneCustomer, ACCOUNT_NUMBER_1, ACCOUNT_NUMBER_2, amount);
        	fail();
        } catch (IllegalArgumentException ex) {
        	//Then
        	final String actual = ex.getMessage();
        	final String expected = "Customer " + noneCustomer + " does not exist.";
			assertEquals(expected, actual);
        }
    }
    
    @Test
    public void transferBetweenCustomerAccountsThrowsIfFromAccountDoesNotExist() {
    	//Given
    	final Bank bank = new Bank();
    	final Customer customer = bank.addCustomer(CUSTOMER_NAME);
		final double amount = 100.00;		
		final String noneAccountNumber = "John";
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1));
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_2));

        try {
        	//When
        	bank.transferBetweenCustomerAccounts(CUSTOMER_NAME, noneAccountNumber, ACCOUNT_NUMBER_2, amount);
        	fail();
        } catch (IllegalArgumentException ex) {
        	//Then
        	final String actual = ex.getMessage();
        	final String expected = "Account " + noneAccountNumber + " does not exist.";
			assertEquals(expected, actual);
        }
    }
    
    @Test
    public void transferBetweenCustomerAccountsThrowsIfToAccountDoesNotExist() {
    	//Given
    	final Bank bank = new Bank();
    	final Customer customer = bank.addCustomer(CUSTOMER_NAME);
		final double amount = 100.00;		
		final String noneAccountNumber = "John";
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_1));
        bank.openAccount(customer.getName(), AccountsTestHelper.createCheckingAcount(ACCOUNT_NUMBER_2));

        try {
        	//When
        	bank.transferBetweenCustomerAccounts(CUSTOMER_NAME, ACCOUNT_NUMBER_1, noneAccountNumber, amount);
        	fail();
        } catch (IllegalArgumentException ex) {
        	//Then
        	final String actual = ex.getMessage();
        	final String expected = "Account " + noneAccountNumber + " does not exist.";
			assertEquals(expected, actual);
        }
    }
}
