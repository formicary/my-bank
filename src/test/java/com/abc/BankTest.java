package com.abc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private Bank bank;
    
    @Before
    public void initialise() {
        bank = new Bank();
    }
    
    @After
    public void tearDown() {
    	bank = null;
    }
    
    @Test
    public void customerSummary() {
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void customerSummaryWithTwoAccounts() {
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.AccountType.CHECKING));
        john.openAccount(new Account(Account.AccountType.SAVINGS));

        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (2 accounts)", bank.customerSummary());
    }    

    @Test
    public void testTotalInterestPaid() {
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        
        Customer bill = new Customer("Bill");
        Customer oscar = new Customer("Oscar");
        Customer joe = new Customer("Joe");

        bill.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);
        joe.openAccount(maxiSavingsAccount);
        
        bank.addCustomer(bill);
        bank.addCustomer(oscar);
        bank.addCustomer(joe);
        
        checkingAccount.deposit(100.0);
        savingsAccount.deposit(1500.0);
        maxiSavingsAccount.deposit(3000.0);
        
        assertEquals(152.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkingAccount() {
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill");
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void savingsAccountUnderOneThousand() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(savingsAccount);
        bank.addCustomer(bill);

        savingsAccount.deposit(150.0);

        assertEquals(0.15, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccountHighRate() {
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);

        maxiSavingsAccount.deposit(3000.0);

        assertEquals(150.0 , bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void maxiSavingsAccountHighRateWithWidthdraw() {
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);
        
    	Calendar c = Calendar.getInstance();
    	Date currentDate = c.getTime();
    	c.setTime(currentDate);
    	
    	c.add(Calendar.DAY_OF_MONTH, -11);
        Date date10daysBefore= (c.getTime());
        
        //add a deposit from 11 days ago
        Transaction transaction10daysBefore = new Transaction(3100.0, date10daysBefore); 
        maxiSavingsAccount.addTransaction(transaction10daysBefore);
        
        //make withdraw
        Transaction widthdraw = new Transaction(-100.0, date10daysBefore); 
        maxiSavingsAccount.addTransaction(widthdraw);  

        assertEquals(150.0 , bank.totalInterestPaid(), DOUBLE_DELTA);
    }
        
    @Test
    public void maxiSavingsAccountLowRate() {
        Account maxiSavingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        Customer bill = new Customer("Bill");
        bill.openAccount(maxiSavingsAccount);
        bank.addCustomer(bill);
        
    	Calendar c = Calendar.getInstance();
    	Date currentDate = c.getTime();
    	c.setTime(currentDate);
    	
    	c.add(Calendar.DAY_OF_MONTH, -11);
        Date date10daysBefore= (c.getTime());
        
        //add a deposit from 11 days ago
        Transaction transaction10daysBefore = new Transaction(3100.0, date10daysBefore); 
        maxiSavingsAccount.addTransaction(transaction10daysBefore);
        
        //make withdraw
        Transaction widthdraw = new Transaction(-100.0); 
        maxiSavingsAccount.addTransaction(widthdraw);  
        
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void getFirstCustomerTest() {
    	  Account checkingAccount = new Account(Account.AccountType.CHECKING);
          Account savingsAccount = new Account(Account.AccountType.SAVINGS);
          
          Customer bill = new Customer("Bill");
          Customer oscar = new Customer("Oscar");

          bill.openAccount(checkingAccount);
          oscar.openAccount(savingsAccount);
          
          bank.addCustomer(bill);
          bank.addCustomer(oscar);
          
          String firstCustomer = bank.getFirstCustomer();
          
          assertEquals(firstCustomer, "Bill");
    }
    
    @Test
    public void getFirstCustomerWithNoCustomersTest() {
    	  String firstCustomer = bank.getFirstCustomer();
          
          assertEquals(firstCustomer, "No customers");
    }
}