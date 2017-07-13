/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abc;

import com.abc.Account.AccountType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author comet
 */
public class AccountTest {

    @Test(expected = IllegalArgumentException.class)
    public void testDepositNegativeAmount() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(-5.0);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNegativeAmount(){
        Account account = new Account(AccountType.CHECKING);
        account.withdraw(-5.0);
    }
    
    @Test
    public void testWithdrawEmptyAccount(){
        Account account = new Account(AccountType.CHECKING);
        
        boolean expectedMethod = false;
        boolean resultMethod = account.withdraw(1.0);
        
        assertEquals(expectedMethod, resultMethod);
        
        double expected = 0.0;
        double result = account.sumTransactions();
        
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    public void testSumTransactions(){
        Account account = new Account(AccountType.CHECKING);
        
        account.deposit(100.0);
        account.withdraw(75.0);
        account.deposit(20);
        
        double expected = 45.0;
        double result = account.sumTransactions();
        
        assertEquals(expected, result, 0.001);
    }
}
