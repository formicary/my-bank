package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import com.abc.Transaction;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5, "Null");
        assertTrue(t instanceof Transaction);
    }

    // Deposit
    @Test
    public void depositAmount() {
        Account checking = new Account(Account.accountType.CHECKING);
        checking.deposit(500);
        assertEquals(500, checking.sumTransactions());
    }

    // Deposit a negative amount
    @Test(expected = IllegalArgumentException.class)
    public void depositNegativeAmount(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	checking.deposit(-100);
    }
    
    // Withdraw
    @Test
    public void withdraw(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	//Ensure balance is greater than 0
    	checking.deposit(200);
    	checking.withdraw(100);
    	assertEquals(100, checking.sumTransactions());
    }

    // Withdraw a negative amount
    @Test(expected = IllegalArgumentException.class)
    public void withdrawNegative(){
    	Account checking = new Account(Account.accountType.CHECKING);
    	checking.withdraw(-100);
    }
}
