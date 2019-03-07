package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankTest {
	
	private Bank bank;
	private Customer john;
	private String customerName = "John";
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init() {
		bank = new Bank();
        john = new Customer(customerName);
	}
    
    @Test
    public void AddCustomer() {
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        assertTrue("customers ArrayList contains customer John", bank.getCustomers().contains(john));
    }

    @Test
    public void CustomerSummary() {
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer summary must match format with customerName"
        		,"Customer Summary\n - " + customerName + " (1 account)", bank.customerSummary());
    }
    
    @Test
    public void CustomerSummary_TwoAccounts() {

    	john.openAccount(new Account(Account.CHECKING));
    	john.openAccount(new Account(Account.SAVINGS));
    	bank.addCustomer(john);
    	
    	assertEquals("Customer summary must match following string with name and the word account formatted to accounts"
    			,"Customer Summary\n - " + customerName + " (2 accounts)", bank.customerSummary());
    }
    

    @Test
    public void totalInterestPaid_checkingAccount() {
        Account checkingAccount = new Account(Account.CHECKING);
        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        checkingAccount.deposit(100.0);

        assertEquals("Zero days since first transaction so no interest paid"
        		,0, bank.totalInterestPaid(), ConstantsTest.DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_savingsAccount_LessThan1000() {
        Account checkingAccount = new Account(Account.SAVINGS);
        john.openAccount(checkingAccount);
        bank.addCustomer(john);

        checkingAccount.deposit(150.0);

        assertEquals("Zero days since first transaction so no interest paid\""
        		,0, bank.totalInterestPaid(), ConstantsTest.DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_savingsAccount_MoreThan1000() {
        Account savingsAccount = new Account(Account.SAVINGS);
        john.openAccount(savingsAccount);
        bank.addCustomer(john);

        savingsAccount.deposit(1500.0);

        assertEquals("balance over 1000 so 1 total interest is paid"
        		,1, bank.totalInterestPaid(), ConstantsTest.DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_MaxiSavingsAccount() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        john.openAccount(maxiSavingsAccount);
        bank.addCustomer(john);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals("Zero days since first deposit so no interest paid"
        		,0, bank.totalInterestPaid(), ConstantsTest.DOUBLE_DELTA);
    }
    
    @Test
    public void getCustomerFirst() {
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        assertTrue("First customer name is equal to the first customer, John", bank.getFirstCustomer().equals(customerName));
    }
    
    @Test
    public void GetCustomerFirst_WhenNoCustomers() {
    	exception.expect(IndexOutOfBoundsException.class);
    	exception.expectMessage("No customers found belonging to this bank");
    	
    	bank.getFirstCustomer();
    }

}
