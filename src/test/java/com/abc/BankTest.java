package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void totalInterest() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        Account checkingAccount = new CheckingAccount();
        john.openAccount(checkingAccount);
        checkingAccount.deposit(1500.0);
        
        Customer dave = new Customer("John");
        Account savingsAccount = new SavingsAccount();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        dave.openAccount(savingsAccount);
        dave.openAccount(maxiSavingsAccount);
        savingsAccount.deposit(1500.0);
        maxiSavingsAccount.deposit(1500.0);
        
        bank.addCustomer(john);
        bank.addCustomer(dave);
        
        assertEquals(78.5, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
