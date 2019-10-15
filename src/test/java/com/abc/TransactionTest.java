package com.abc;

import org.junit.Test;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void transaction() {
   		Account checkingAccount = new Account(Account.CHECKING);
        Transaction t = new Transaction(checkingAccount,50,Transaction.DEPOSIT);
        assertTrue(t instanceof Transaction);
    }
    @Test
    public void depositTest() {
    		Account checkingAccount = new Account(Account.CHECKING);
    		checkingAccount.deposit(1000.0);
    		
    		assertEquals(1000,checkingAccount.balance,DOUBLE_DELTA);
    }
    @Test
    public void withdrawlTest() {
    		Account checkingAccount = new Account(Account.CHECKING);
    		checkingAccount.deposit(1000.0);
    		checkingAccount.withdraw(500);
    		assertEquals(500,checkingAccount.balance,DOUBLE_DELTA);
    		
    }
    
    @Test
    public void transferTest() {
    		Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        Account checkingAccount = new Account(Account.CHECKING);
        
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount).openAccount(checkingAccount));
        
        savingsAccount.deposit(1000);
        savingsAccount.transferBetweenAccounts(checkingAccount, 500);
        
        assertEquals(500,checkingAccount.balance,DOUBLE_DELTA);
        assertEquals(500,savingsAccount.balance,DOUBLE_DELTA);
    }
}
