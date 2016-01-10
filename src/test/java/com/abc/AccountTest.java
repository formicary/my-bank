package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test (expected = Exception.class)
    public void testDepositNegativeAmount() {
        Account a = new SavingsAccount();
        a.deposit(-100);
    }
    
    @Test (expected = Exception.class)
    public void testWithdrawNegativeAmount() {
        Account a = new SavingsAccount();
        a.withdraw(-100);
    }
    
    @Test
    public void testGetAccountType1() {
        Account a = new MaxiSavingsAccount();
        assertEquals("Maxi Savings Account", a.getAccountType());
    }
    
    @Test
    public void testGetAccountType2() {
        Account a = new CheckingAccount();
        assertEquals("Checking Account", a.getAccountType());
    }
    
}
