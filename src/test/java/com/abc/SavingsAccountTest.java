package com.abc;

import org.junit.Test; 

import static org.junit.Assert.assertEquals;

import java.util.*;

public class SavingsAccountTest{

    
    private static final double DOUBLE_DELTA = 1e-15;


    @Test
    public void testFirstRate(){
        
        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -10);
        
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);
        provider.reset();

        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
 
        assertEquals(1.0, interest, DOUBLE_DELTA);
    }


    @Test
    public void testSecondRate(){
        
        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -10);
        
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(1100.0);
        provider.reset();
        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
 
        assertEquals(12.11, interest, DOUBLE_DELTA);
    }


    @Test
    public void testMultipleTransactions(){
        
        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -20);
        
        Bank bank = new Bank();
        Account checkingAccount = new SavingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        // 100 at 0.1% for 10 days
        checkingAccount.deposit(100.0);

        // 101 at 0.2% for 10 days
        provider.add(Calendar.DAY_OF_MONTH, 10);
        checkingAccount.deposit(1000);

        provider.reset();
        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
 
        assertEquals(13.13, interest, DOUBLE_DELTA);
    }

}