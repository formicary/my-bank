package com.abc;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(AccountType.CHECKING));
        bank.addCustomer(john);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -10);
        
        Transaction t = Mockito.mock(Transaction.class);
        Date date = c.getTime();
        
        Mockito.when(t.getDate()).thenReturn(date);
        
        // DEPOSIT 10000, ten days earlier 
        Mockito.when(t.getAmount()).thenReturn(10000.0);
        checkingAccount.depositWithdraw(t);
        
        checkingAccount.withdraw(5000);
        
        checkingAccount.deposit(100);
                        
        assertEquals(0.27, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bank.addCustomer(bill);
        
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -20);
        
        Transaction t = Mockito.mock(Transaction.class);
        Date date = c.getTime();
        
        Mockito.when(t.getDate()).thenReturn(date);
        
        Mockito.when(t.getAmount()).thenReturn(10000.0);
        savingsAccount.depositWithdraw(t);
        

        savingsAccount.withdraw(10000);
        

        assertEquals(1.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account maxiSAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill.openAccount(maxiSAccount));
        
        Calendar c = Calendar.getInstance();
        
        // will test case 10 days since last withdrawal, interest 5%
        c.add(Calendar.DATE, -10);
        
        
        Transaction t = Mockito.mock(Transaction.class);
        Date date = c.getTime();
        
        Mockito.when(t.getDate()).thenReturn(date);
        
        Mockito.when(t.getAmount()).thenReturn(10000.0);
        maxiSAccount.depositWithdraw(t);
        
        maxiSAccount.withdraw(1);
        
        assertEquals(13.71, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxi_savings_account2() {
        Bank bank = new Bank();
        Account maxiSAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill.openAccount(maxiSAccount));
        
        Calendar c = Calendar.getInstance();
        
        // will test case less than 10 days since last withdrawal, interest 0.1%
        c.add(Calendar.DATE, -9);
        
        
        Transaction t = Mockito.mock(Transaction.class);
        Date date = c.getTime();
        
        Mockito.when(t.getDate()).thenReturn(date);
        
        // depositing 10000, 9 days earlier
        Mockito.when(t.getAmount()).thenReturn(10000.0);
        maxiSAccount.depositWithdraw(t);
                
        assertEquals(0.25, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void testFirstCustomer() {
    	Bank bank = new Bank();
        Account maxiSAccount = new Account(AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        Customer andy = new Customer("Andy");
        bank.addCustomer(bill.openAccount(maxiSAccount));
        bank.addCustomer(andy.openAccount(maxiSAccount));
        
        assertEquals("Bill", bank.getFirstCustomer());
    }
}
