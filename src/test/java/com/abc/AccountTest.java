package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

	private static final double DOUBLE_DELTA = 1e-15;

    @Test
	public void dailyInterestChecking() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        CheckingAccount account = new CheckingAccount();
        john.openAccount(account);
        bank.addCustomer(john);
	    
	    account.deposit(100.0);
	    account.deposit(account.interestEarnedDaily());
	    
	    double expected = 100.0 + 100*(0.001/365.0);
	    assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
	}
    @Test
	public void dailyInterestSavings() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        SavingsAccount account = new SavingsAccount();
        john.openAccount(account);
        bank.addCustomer(john);
	    
	    account.deposit(5000.0);
	    account.deposit(account.interestEarnedDaily());
	    
	    
	    double expected = 5000.0 + (1000*0.001 + 4000*0.002)/365.0;
	    assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
	}
    @Test
	public void dailyInterestMaxiBase() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        MaxiSavingsAccount account = new MaxiSavingsAccount();
        john.openAccount(account);
        bank.addCustomer(john);
	    
	    account.deposit(200.0);
	    account.withdraw(100.0);
	    account.deposit(account.interestEarnedDaily());
	    
	    double expected = 100.0 + 100*(0.001/365.0);
	    assertEquals(true, account.HasWithdrawnPast10days());
	    assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
	}
    
    @Test
    public void doubleDailyInterestChecking() {
    	Bank bank = new Bank();
    	CheckingAccount account = new CheckingAccount();
        bank.addCustomer(new Customer("Bill").openAccount(account));
        
        account.deposit(100.0);
	    account.deposit(account.interestEarnedDaily());
	    account.deposit(account.interestEarnedDaily());
        
        double dayOne = 100.0 + 100*(0.001/365.0);
        double expected = 100.0 + 100*(0.001/365.0) + ((dayOne * 0.001)/365.0);
        
        assertEquals(expected, account.sumTransactions(), DOUBLE_DELTA);
}
}
