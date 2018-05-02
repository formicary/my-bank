package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-2;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(new checkingAccount(john), bank);
        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {	
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        checkingAccount billAccount = new checkingAccount (bill);
        bill.openAccount(billAccount, bank);
        
        Customer john = new Customer("John");
        bank.addCustomer(john);  
        checkingAccount johnAccount = new checkingAccount(john);
        john.openAccount(johnAccount, bank);
             
        billAccount.deposit(100.0);
        johnAccount.deposit(100.0);
 
        assertEquals(0.2, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savings_account() {
    		Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        savingsAccount billAccount = new savingsAccount(bill);
        bill.openAccount(billAccount, bank);
               
        Customer john = new Customer("John");
        bank.addCustomer(john);     
        savingsAccount johnAccount = new savingsAccount(john);
        john.openAccount(johnAccount, bank);
                 
        billAccount.deposit(1500.0);
        johnAccount.deposit(1500.0);

        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxi_savings_account() {
    		Bank bank = new Bank();
        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        maxisavingsAccount billAccount = new maxisavingsAccount(bill);
        bill.openAccount(billAccount, bank);
     
        billAccount.deposit(3000.0);    

        assertEquals(153.8, bank.totalInterestPaid(), DOUBLE_DELTA);
        
        billAccount.withdraw(10.0);
        assertEquals(153.8, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
    
    @Test
    public void getfirstcustomer() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        bank.addCustomer(john);
        john.openAccount(new checkingAccount(john), bank);

        Customer bill = new Customer("Bill");
        bank.addCustomer(bill);
        bill.openAccount(new checkingAccount(bill), bank);
                
        assertEquals("John", bank.getFirstCustomer());        
    }

	@Test
    public void deposit_withdrawl() {
    		Bank bank = new Bank();
    		Customer bill = new Customer("Bill");
    		bank.addCustomer(bill);
        Account billAccount = new checkingAccount(bill);
        bill.openAccount(billAccount, bank);
               
        assertEquals(true, billAccount.deposit(100.0));
        assertEquals(100.0, billAccount.getAccountBalance(), DOUBLE_DELTA);
        
        billAccount.withdraw(50.0);      
        assertEquals(50.1, billAccount.getAccountBalance(), DOUBLE_DELTA);
        
        assertEquals(false, billAccount.withdraw(100.0));
        
        assertEquals(50.1, billAccount.getAccountBalance(), DOUBLE_DELTA);  		
    }

}
