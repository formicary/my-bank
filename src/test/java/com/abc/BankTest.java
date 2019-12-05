package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    // check for correct first customer name, and that exceptions are thrown correctly
    @Test
    public void testFirstCustomer() {
        Bank bank = new Bank();   
        
        try {
        	bank.getFirstCustomer();
        	fail("Exception not thrown");
        } catch (Exception expectedException) { }
        
        bank.addCustomer(new Customer("John")); 
        bank.addCustomer(new Customer("Jack")); 
        assertEquals("John", bank.getFirstCustomer());
    }
    @Test
    public void customerSummary() {
        Bank bank = new Bank(); 
        Customer john = new Customer("John");
        
        bank.addCustomer(john);  
        
        int n = new Random().nextInt(10);	// opens random number of accounts
        for (int i=0; i<n; i++) {
        	john.openAccount(Account.Type.CHECKING);
        }    

        assertEquals("Customer Summary\n - John ("+n+" account"+(n!=1? "s": "")+")", bank.customerSummary());
    }

    // check that the bank pays correct interests to checking accounts
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");   
        bank.addCustomer(bill);   
        String accountID = bill.openAccount(Account.Type.CHECKING);
        
        int n = new Random().nextInt(100000);
        bill.deposit(accountID, n);
        
        bank.payInterests();  
        assertEquals(twoDecimal(n*0.001), bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(twoDecimal(n*0.001), bank.getLastInterestPaid(), DOUBLE_DELTA);
    }

    // check that the bank pays correct interests to saving accounts
    @Test
    public void savings_account() {
    	Bank bank = new Bank();
        Customer bill = new Customer("Bill");   
        bank.addCustomer(bill);   
        String accountID = bill.openAccount(Account.Type.SAVINGS);
        
        int n = new Random().nextInt(100000);
        bill.deposit(accountID, n);
        
        bank.payInterests();  
        double res = n<=1000? n*0.001 : 1 + (n-1000)*0.002;
        double i = twoDecimal(res); 
        assertEquals(i, bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(i, bank.getLastInterestPaid(), DOUBLE_DELTA);
    }

    // check that the bank pays correct interests to maxi saving accounts
    @Test
    public void maxi_savings_account() {
    	Bank bank = new Bank();
        Customer bill = new Customer("Bill");   
        bank.addCustomer(bill);   
        String accountID = bill.openAccount(Account.Type.MAXI_SAVINGS);
        
        int n = new Random().nextInt(50000)+50000;
        bill.deposit(accountID, n);
        
        bank.payInterests();  
        double i = twoDecimal(n*0.05); 
        assertEquals(i, bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(i, bank.getLastInterestPaid(), DOUBLE_DELTA);
        
        int n1 = new Random().nextInt(50000);
        bill.withdraw(accountID, n1);
        
        bank.payInterests();  

        double i1 = twoDecimal((n+i-n1)*0.001); 
        double total_i = twoDecimal(i1+i); 
        
        assertEquals(total_i, bank.getTotalInterestPaid(), DOUBLE_DELTA);
        assertEquals(i1, bank.getLastInterestPaid(), DOUBLE_DELTA);
    }
    
    // check the the scheduler works correctly and triggers balance update on time
    @Test
    public void schedulerTest() throws InterruptedException {
    	Bank bank = new Bank();
        Customer bill = new Customer("Bill");   
        bank.addCustomer(bill);   
        String accountID = bill.openAccount(Account.Type.CHECKING);
        
        int n = new Random().nextInt(50000);
        bill.deposit(accountID, n);
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND)+1);	// update in 1 second
        bank.scheduleInterests(calendar);
        
        TimeUnit.SECONDS.sleep(1);
        assertEquals(twoDecimal(n*0.001), bank.getLastInterestPaid(), DOUBLE_DELTA);

    }
    
	// helper method for 2 decimal precision
    private double twoDecimal(double n) {
    	return Double.parseDouble(String.format("%.2f", n)); 
    }

}
