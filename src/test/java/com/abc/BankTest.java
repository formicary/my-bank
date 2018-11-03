package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;


public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    InterestCalculator calculator = new YearlyInterestCalculator();
    Bank bank;
    Account account;
    Customer john;
    
    @Before
    public void init() {
    	bank = new Bank();
    	john = new Customer("John");
    }

    @Test
    public void customerSummary_correctString() {
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    
    @Test
    public void customerSummary_noCustomers() {
        assertEquals("Bank has no customers!", bank.customerSummary());
    }

    
    @Test
    public void totalInterestPaid_checkingAccount() {
        account = new Account(AccountType.CHECKING);
        bank.addCustomer(john.openAccount(account));
        account.deposit(100.0);
        
        assertEquals(0.1, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
    }

    
    @Test
    public void totalInterestPaid_savingsAccount() {
        account = new Account(AccountType.SAVINGS);
        bank.addCustomer(john.openAccount(account));
        account.deposit(1500.0);
        
        assertEquals(2.0, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
    }
    
    
    @Test
    public void totalInterestPaid_maxiSavingsAccount() {
        account = new Account(AccountType.MAXI_SAVINGS);
        bank.addCustomer(john.openAccount(account));
        account.deposit(3000.0);

        assertEquals(170.0, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
    }
    
    
    @Test
    public void totalInterstPaid_noTransactions() {
    	account = new Account(AccountType.CHECKING);
    	bank.addCustomer(john.openAccount(account));
    	    	
    	assertEquals(0, bank.totalInterestPaid(calculator), DOUBLE_DELTA);
    }
    
    
    @Test
    public void getFirstCustomer_correctFirstCustomerName() {
    	Customer second = new Customer("second");
    	Customer third = new Customer("third");
    	Customer fourth = new Customer("fourth");
    	
    	bank.addCustomer(john);
    	bank.addCustomer(second);
    	bank.addCustomer(third);
    	bank.addCustomer(fourth);
    	
    	assertEquals("John", bank.getFirstCustomer());
    }
    
    
    @Test
    public void getFirstCustomer_noCustomers() {    	
    	assertEquals("Bank has no customers", bank.getFirstCustomer());
    }
}