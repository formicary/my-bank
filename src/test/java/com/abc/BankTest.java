package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    
	@Rule
	public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void AddCustomer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        assertTrue("customers ArrayList contains customer John", bank.getCustomers().contains(john));
    }

    @Test
    public void CustomerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void CustomerSummary_TowAccounts() {
    	Bank bank = new Bank();
    	Customer john = new Customer("John");
    	john.openAccount(new Account(Account.CHECKING));
    	john.openAccount(new Account(Account.SAVINGS));
    	bank.addCustomer(john);
    	
    	assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }
    

    @Test
    public void totalInterestPaid_checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals("Zero days since first transaction so no interest paid"
        		,0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_savingsAccount_LessThan1000() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(150.0);

        assertEquals("Zero days since first transaction so no interest paid\""
        		,0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_savingsAccount_MoreThan1000() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1500.0);

        assertEquals("balance over 1000 so 1 total interest is paid"
        		,1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void totalInterestPaid_MaxiSavingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(3000.0);

        assertEquals("Zero days since first deposit so no interest paid"
        		,0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void getCustomerFirst() {
    	String customerName = "John";
    	
    	Bank bank = new Bank();
        Customer john = new Customer(customerName);
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);
        
        assertTrue("First customer name is equal to the first customer, John", bank.getFirstCustomer().equals(customerName));
    }
    
    @Test
    public void GetCustomerFirst_WhenNoCustomers() {
    	
    	Bank bank = new Bank();
    	
    	exception.expect(IndexOutOfBoundsException.class);
    	exception.expectMessage("No customers found belonging to this bank");
    	
    	bank.getFirstCustomer();
    }

}
