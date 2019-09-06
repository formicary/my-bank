package com.abc;

import org.junit.Test; 

import static org.junit.Assert.assertEquals;

import java.util.*;

public class MaxiSavingsAccountTest{

    
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testHighRate(){
        
        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -10);
        
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        // 100 at 5% for 10 days
        maxiSavingsAccount.deposit(100.0);
        provider.reset();

        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
 
        assertEquals(62.89, interest, DOUBLE_DELTA);
    }

    @Test
    public void testLowRate(){
        
        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -10);
        
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        Customer bill = new Customer("Bill").openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        // 100 at 0.1% for 10 days
        maxiSavingsAccount.deposit(200.0);
        maxiSavingsAccount.withdraw(100);
        provider.reset();

        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
 
        assertEquals(1.00, interest, DOUBLE_DELTA);
    }

    @Test
    public void testMultipleTransaction() {

        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -20);
        
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        // 5% for 10 days
        maxiSavingsAccount.deposit(1100.0);

        // 0.1% for 10 days
        provider.add(Calendar.DAY_OF_MONTH, 10);
        maxiSavingsAccount.withdraw(100.0);

        provider.reset();
        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
        
        assertEquals(708.78, interest, DOUBLE_DELTA);
    }


    @Test
    public void testMultipleWithdrawals() {

        DateProvider provider = DateProvider.getInstance();
        provider.add(Calendar.DAY_OF_MONTH, -30);
        
        Bank bank = new Bank();
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        // 5% for 10 days
        maxiSavingsAccount.deposit(100.0);

        // 0.1% for 7 days
        provider.add(Calendar.DAY_OF_MONTH, 10);
        maxiSavingsAccount.withdraw(10.0);

        // 0.1 for another 10 days        
        provider.add(Calendar.DAY_OF_MONTH, 7);
        maxiSavingsAccount.withdraw(10.0);

        // 5% for 3 days
        provider.reset();

        double interest = Math.round(bank.totalInterestPaid() * 100.0) / 100.0;
        
        assertEquals(88.33, interest, DOUBLE_DELTA);
    }
    
}