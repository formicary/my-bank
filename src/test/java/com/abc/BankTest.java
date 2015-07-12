package com.abc;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() {
        Bank bank = new Bank();
        Account savingsAccount = new Account(Account.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

        savingsAccount.deposit(1500.0);

        assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() {
        Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

        maxiSavingsAccount.deposit(3000.0);
        
        assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);      
    }
    
    @Test
    // Test interest when withdrawal made in last 10 days
    public void maxiSavingsAccount2() {
    	Bank bank = new Bank();
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));
        
        maxiSavingsAccount.deposit(4000.0);
        maxiSavingsAccount.withdraw(1000.0);
        System.out.println(bank.totalInterestPaid());
        
        assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);   
    }
    
    
    
    @Test
    public void withdrawalsTenDaysTrue() {
    	Bank bank = new Bank();
    	Account checkingAccount = new Account(Account.CHECKING);
    	Customer bill = new Customer("Bill").openAccount(checkingAccount);
    	bank.addCustomer(bill);
    	
    	// Deposit 200 so not empty
    	checkingAccount.deposit(200.0);
    	// Make withdraw transaction today (therefore within past 10 days)
    	checkingAccount.withdraw(100.0);
    	
    	// Money withdrawn today - should return true
    	assertEquals(true, checkingAccount.withdrawalsPastTenDays());
    }
    
    @Test
    public void withdrawalsTenDaysFalse() {
    	Bank bank = new Bank();
    	Account checkingAccount = new Account(Account.CHECKING);
    	Customer bill = new Customer("Bill").openAccount(checkingAccount);
    	bank.addCustomer(bill);
    	Date today = DateProvider.getInstance().now();
    	
    	// Deposit and withdraw transactions
    	checkingAccount.deposit(1000.0);
    	checkingAccount.withdraw(150.0);
    	
    	// Calculate deposit date as over 10 days ago (100 days ago)
    	Date depositDate = new Date(today.getTime() - TimeUnit.DAYS.toMillis(100));
    	// Calculate withdrawal date as over 10 days ago (50 days ago)
    	Date withdrawalDate = new Date(today.getTime() - TimeUnit.DAYS.toMillis(50));
    	
    	// Retrieve deposit transaction and set date
    	Transaction depositTrans = checkingAccount.transactions.get(0);
    	depositTrans.setTransactionDate(depositDate);
    	
    	// Retrieve withdrawal transaction and set date
    	Transaction withdrawalTrans = checkingAccount.transactions.get(1);
    	withdrawalTrans.setTransactionDate(withdrawalDate);
    	
    	// Money withdrawn over 10 days ago - should return false
    	assertEquals(false, checkingAccount.withdrawalsPastTenDays());
    		
    }
    
    @Test
    public void transferBetweenAccounts() {
        Bank bank = new Bank();
        Customer bill = new Customer("Bill");

        // Create checking and savings accounts for Bill
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        // Open checking and savings accounts for Bill
        bill.openAccount(checkingAccount);
        bill.openAccount(savingsAccount);

        // Deposit 100 in checking account and 300 in savings account
        checkingAccount.deposit(100);
        savingsAccount.deposit(300);

        // Add Bill as customer to the bank
        bank.addCustomer(bill);

        // Transfer 30 from savings account to checking account
        bill.transferBetweenAccounts(savingsAccount, checkingAccount, 30.0);
        
        assertTrue((savingsAccount.sumTransactions() == 270.0) && (checkingAccount.sumTransactions() == 130.0));
    }

}
