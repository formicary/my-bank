package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-2;
	
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
    
    @Test
    public void transfer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        
        checkingAccount checkingAccount = new checkingAccount(john);
        savingsAccount savingsAccount = new savingsAccount(john);
        maxisavingsAccount maxi = new maxisavingsAccount(john);
        john.openAccount(checkingAccount, bank).openAccount(savingsAccount, bank);
        
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(50);
        
        assertEquals(true,john.transfer(50, savingsAccount, checkingAccount));
        assertEquals(false,john.transfer(60.0, savingsAccount, maxi));
        
        assertEquals(150.1, checkingAccount.getAccountBalance(), DOUBLE_DELTA);

        
    }
}
