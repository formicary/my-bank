package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Ignore;

public class BankTest {
	
    @Test
    public void customerSummary() {
    	
        Bank bank = new Bank();
        Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Customer john = new Customer("John").openAccount(checkingAccount);
        
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() {
        
    	Bank bank = new Bank();  
        Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);  
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);
        
        checkingAccount.deposit(100.0);

        BigDecimal interest = new BigDecimal(0.1).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(interest, bank.totalInterestPaid());
    }

    @Test
    public void savingsAccount() {
    	
        Bank bank = new Bank();      
        Account savingAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);   
        bank.addCustomer(new Customer("Bill").openAccount(savingAccount));
        
        savingAccount.deposit(1500.0);

        BigDecimal interest = new BigDecimal(2.0).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(interest, bank.totalInterestPaid());
    }

    @Test
    public void maxiSavingsAccount() {
    	
        Bank bank = new Bank();
        Account maxiSavingAccount = AccountFactory.getAccount(AccountType.MAXI_SAVINGS_ACCOUNT);
        bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));
        
        maxiSavingAccount.deposit(3000.0);

        BigDecimal interest = new BigDecimal(150.0).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(interest, bank.totalInterestPaid());
    }
    
    @Test
    public void compundDailyInterest(){

    	Bank bank = new Bank();
    	
    	Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
    	Account savingsAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);
    	Account maxiSavingsAccount = AccountFactory.getAccount(AccountType.MAXI_SAVINGS_ACCOUNT);

        Customer henry = new Customer("Henry")
        		.openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);
        bank.addCustomer(henry);
        
        checkingAccount.deposit(1000.0);	//0.0027 daily = 0.00$
        savingsAccount.deposit(4000.0);	//0.02 daily
        maxiSavingsAccount.deposit(300); //0.04 daily
        
        BigDecimal interest = new BigDecimal(5300.06).setScale(2, BigDecimal.ROUND_HALF_UP);
        assertEquals(interest, bank.totalDailyCompound());
    }
    
    @Test
    public void compundDailyInterestToYear(){

    	Bank bank = new Bank();
    	Account checkingAccount = AccountFactory.getAccount(AccountType.CHECKING_ACCOUNT);
        Account savingsAccount = AccountFactory.getAccount(AccountType.SAVINGS_ACCOUNT);
    	Account maxiSavingsAccount = AccountFactory.getAccount(AccountType.MAXI_SAVINGS_ACCOUNT);
    	Customer henry = new Customer("Henry").openAccount(checkingAccount)
    						.openAccount(savingsAccount).openAccount(maxiSavingsAccount);
        bank.addCustomer(henry);
        
        checkingAccount.deposit(10000.0);	// 10010.95
        savingsAccount.deposit(10000.0);		// 10018.25
        maxiSavingsAccount.deposit(300);	// 314.6

        BigDecimal interestCheckings = new BigDecimal(10010.95).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal interestSavings = new BigDecimal(10018.25).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal interestMaxiSavings = new BigDecimal(314.6).setScale(2, BigDecimal.ROUND_HALF_UP);

        for (int i = 0; i < 365; i++)
        	bank.totalDailyCompound();
        
        assertEquals(interestCheckings, checkingAccount.getBalance());
        assertEquals(interestSavings, savingsAccount.getBalance());
        assertEquals(interestMaxiSavings, maxiSavingsAccount.getBalance());
    }
    
}
