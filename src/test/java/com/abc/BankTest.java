package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

public class BankTest {
   // private static final double DOUBLE_DELTA = 1e-15;
    private static final double DOUBLE_DELTA = 0.5e-2;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new CheckingsAccount());
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }
    
    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new CheckingsAccount();
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(10000.0);
        
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t = checkingAccount.getTransactions();
        t.get(0).setTransactionDate(lastYear);
        

        assertEquals(10.00, bank.totalInterestPaid(), DOUBLE_DELTA);
       // assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
        
    }
    
    

    @Test
    public void savings_account() {
        Bank bank = new Bank();
        Account savingAccount = new SavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

        savingAccount.deposit(2500.0);
        
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t = savingAccount.getTransactions();
        t.get(0).setTransactionDate(lastYear);

        assertEquals(5.50, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

   
     @Test
    public void maxi_savings_account() {
        Bank bank = new Bank();
        Account checkingAccount = new MaxiSavingsAccount();
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        checkingAccount.deposit(1000.0);
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t = checkingAccount.getTransactions();
        t.get(0).setTransactionDate(lastYear);
        assertEquals(51.27, bank.totalInterestPaid(), DOUBLE_DELTA);
    }
     
     @Test
     public void testMultipleAccounts() {
     	Bank bank = new Bank();
     	 Account checkingAccount = new CheckingsAccount();
     	 Account maxiSavingAccount = new MaxiSavingsAccount();
     	 Account savingAccount = new SavingsAccount();
     	
     	 
     	bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
    	bank.addCustomer(new Customer("Adam").openAccount(maxiSavingAccount));
    	bank.addCustomer(new Customer("Jay").openAccount(savingAccount));


     	maxiSavingAccount.deposit(1000);
     	checkingAccount.deposit(10000);
     	savingAccount.deposit(2500);
     	
        Date lastYear = new Date(System.currentTimeMillis() - 365L * 24 * 3600
				* 1000);
        List<Transaction> t1 = checkingAccount.getTransactions();
        t1.get(0).setTransactionDate(lastYear);
        
        List<Transaction> t2 = maxiSavingAccount.getTransactions();
        t2.get(0).setTransactionDate(lastYear);
        
        List<Transaction> t3 = savingAccount.getTransactions();
        t3.get(0).setTransactionDate(lastYear);
        
     	
     	
     	
    assertEquals(66.78, bank.totalInterestPaid(), DOUBLE_DELTA);
     }
}
