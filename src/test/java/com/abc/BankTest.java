package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary_correctString() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    
    @Test
    public void totalInterestPaid_checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(100.0);
        
        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    
    @Test
    public void totalInterestPaid_savingsAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
        checkingAccount.deposit(1500.0);
        
        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    
    @Test
    public void totalInterestPaid_maxiSavingsAccount_lessThanTenDays() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        checkingAccount.deposit(3000.0);
        checkingAccount.withdraw(2000);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void totalInterestPaid_maxiSavings_greaterThanTenDays() {
    		Bank bank = new Bank();
            Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
            checkingAccount.deposit(3500.0);
            checkingAccount.withdraw(500);
            bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
           
            DateProvider.getInstance().addFifthteenDays();
            
            assertEquals(150, bank.totalInterestPaid(), DOUBLE_DELTA);
    	
    }
    
    @Test
    public void totalInterestPaid_maxiSavingsNoWithdrawls_lessThanTenDays() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
        checkingAccount.deposit(1000.0);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        assertEquals(1.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void totalInterestPaid_maxiSavingsNoWithdrawls_greaterThanTenDays() {
    		Bank bank = new Bank();
            Account checkingAccount = new Account(AccountType.MAXI_SAVINGS);
            checkingAccount.deposit(3000.0);
            bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
           
            DateProvider.getInstance().addFifthteenDays();
            
            assertEquals(150, bank.totalInterestPaid(), DOUBLE_DELTA);
    	
    }
    
    
    @Test
    public void totalInterstPaid_noTransactions() {
    	Bank bank = new Bank();
    	Account checkingAccount = new Account(AccountType.CHECKING);
    	bank.addCustomer(new Customer("Bobby").openAccount(checkingAccount));
    	
    	bank.totalInterestPaid();
    	
    	assertEquals(0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void getFirstCustomer_correctFirstCustomerName() {
    	Bank bank = new Bank();
    	Customer first = new Customer("first");
    	Customer second = new Customer("second");
    	Customer third = new Customer("third");
    	Customer fourth = new Customer("fourth");
    	
    	bank.addCustomer(first);
    	bank.addCustomer(second);
    	bank.addCustomer(third);
    	bank.addCustomer(fourth);
    	
    	assertEquals(first.getName(), bank.getFirstCustomer());
    }
    
    
    @Test
    public void getFirstCustomer_noCustomers() {
    	Bank bank = new Bank();
    	
    	assertEquals("Bank has no customers", bank.getFirstCustomer());
    }
}