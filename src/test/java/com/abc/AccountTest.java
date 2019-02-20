package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Locale;
import org.junit.Test;

import consts.Constants;

public class AccountTest {
	
    private static final double DOUBLE_DELTA = 1e-10;
	
    @Test
    public void testDepositWithdraw_CheckingAccount() {
        Account checkingAccount = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
        testAccount(checkingAccount);
    }
    @Test
    public void testDepositWithdraw_SavingsAccount() {
    	Account savingsAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
        testAccount(savingsAccount);
    }
    @Test
    public void testDepositWithdraw_MaxiSavingsAccount() {
    	Account maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.UK);
        testAccount(maxiSavingsAccount);
    }
    
    @Test
    public void testInterestsAccumulated_InOneDay_CheckingAccount() {
    	Account checkingAccount = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
    	double amount = 10000;
    	checkingAccount.deposit(amount);
    	checkingAccount.accrueInterests();
    	double interest = (amount * Constants.ZERO_ONE_PERCENT) / 365;
    	assertEquals(interest, checkingAccount.getInterestEarned(), 0);
    }
    @Test
    public void testInterestsAccumulated_InOneDay_SavingsAccount() {
    	// amount <= 1000
    	Account savingsAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
    	double amount = 1000;
    	savingsAccount.deposit(amount);
    	savingsAccount.accrueInterests();
    	double interest = (amount * Constants.ZERO_ONE_PERCENT) / 365;
    	assertEquals(interest, savingsAccount.getInterestEarned(), 0);
    	// amount > 1000
    	savingsAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
    	amount = 2000;
    	savingsAccount.deposit(amount);
    	savingsAccount.accrueInterests();
    	interest = (1 + (amount-1000) * Constants.ZERO_TWO_PERCENT) / 365;
    	assertEquals(interest, savingsAccount.getInterestEarned(), DOUBLE_DELTA);
    }
    @Test
    public void testInterestsAccumulated_InOneDay_MaxiSavingsAccount() {
    	// last withdrawal > 10 days ago
    	Account maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.UK);
    	double deposit = 10000, withdrawal = 500;
    	maxiSavingsAccount.deposit(deposit);
    	maxiSavingsAccount.withdraw(withdrawal);
    	Transaction lastWithdrawal = maxiSavingsAccount.getLastWithdrawal();
    	// lastWithdrawal can not be older than 10 days, to test this functionality I reverse 
    	// the if/else statement and call the right code anyway.
    	if(lastWithdrawal.isOlder10Days()) {
    		fail();
    	} else {
    		double interestEarned = (
    				maxiSavingsAccount.getTotalAmount() * Constants.FIVE_PERCENT) / 365;
    		double interestToCompare = maxiSavingsAccount.calculateDailyInterest(
    				maxiSavingsAccount.getTotalAmount(), Constants.FIVE_PERCENT);
    		assertEquals(interestEarned, interestToCompare, 0);
    	}
    	// last withdrawal < 10 days ago
    	maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.UK);
    	maxiSavingsAccount.deposit(deposit);
    	maxiSavingsAccount.withdraw(withdrawal);
    	maxiSavingsAccount.accrueInterests();
    	double interest = (maxiSavingsAccount.getTotalAmount() * Constants.ZERO_ONE_PERCENT) / 365;
    	assertEquals(interest, maxiSavingsAccount.getInterestEarned(), 0.000001);
    	// no withdrawals at all : 5% interest rate
    	maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.UK);
    	maxiSavingsAccount.deposit(deposit);
    	maxiSavingsAccount.accrueInterests();
    	interest = (deposit * Constants.FIVE_PERCENT) / 365;
    	assertEquals(interest, maxiSavingsAccount.getInterestEarned(), 0);
    }
    
    @Test
    public void testInterestsAccumulated_In365Days_CheckingAccount() {
    	Account checkingAccount = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
    	double amount = 10000;
    	checkingAccount.deposit(amount);
    	for(int i=0; i<365; i++) {
	    	checkingAccount.accrueInterests();
    	}
    	double interest = amount * Constants.ZERO_ONE_PERCENT;
    	assertEquals(interest, checkingAccount.getInterestEarned(), DOUBLE_DELTA);
    }
    @Test
    public void testInterestsAccumulated_In365Days_SavingsAccount() {
    	Account savingsAccount = new Account(Constants.ACCOUNT_SAVINGS_ID, Locale.UK);
    	double amount = 10000;
    	savingsAccount.deposit(amount);
    	for(int i=0; i<365; i++) {
    		savingsAccount.accrueInterests();
    	}
    	double interest = 1 + (amount-1000) * Constants.ZERO_TWO_PERCENT;
    	assertEquals(interest, savingsAccount.getInterestEarned(), DOUBLE_DELTA);
    }
    @Test
    public void testInterestsAccumulated_In365Days_MaxiSavingsAccount() {
    	Account maxiSavingsAccount = new Account(Constants.ACCOUNT_MAXI_SAVINGS_ID, Locale.UK);
    	double amount = 10000;
    	maxiSavingsAccount.deposit(amount);
    	for(int i=0; i<365; i++) {
    		maxiSavingsAccount.accrueInterests();
    	}
    	double interest = amount * Constants.FIVE_PERCENT;
    	assertEquals(interest, maxiSavingsAccount.getInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void testAccountAmmount() {
    	double d1 = 50.55, d2 = 63.47, w1 = 12.33;
    	Account account = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
    	account.deposit(d1);
    	account.deposit(d2);
    	account.withdraw(w1);
    	assertEquals( (d1+d2-w1), account.getTotalAmount(), 0 );
    }
    
    @Test
    public void testAccountCurrency() {
    	// UK
    	Account account = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.UK);
    	String symbol = account.getCurrencySymbol();
    	assertEquals("£", symbol);
    	// US
    	account = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.US);
    	symbol = account.getCurrencySymbol();
    	assertEquals("$", symbol);
    	// EU
    	account = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.ITALY);
    	symbol = account.getCurrencySymbol();
    	assertEquals("€", symbol);
    	// JAPAN
    	account = new Account(Constants.ACCOUNT_CHECKING_ID, Locale.JAPAN);
    	symbol = account.getCurrencySymbol();
    	assertEquals("￥", symbol);
    }
    
    // Test deposits and withdrawals
    private void testAccount(Account account) {
    	try {
    		account.withdraw(10);
        	fail();
        } catch(Exception e) { }
        try {
        	account.deposit(-10);
        	fail();
        } catch(Exception e) { }

        account.deposit(100.0);
        account.withdraw(50.0);
        account.withdraw(50);
        try {
        	account.withdraw(1);
        	fail();
        } catch(Exception e) { }

        double amount = 105.0;
        account.deposit(amount);
        assertEquals(amount, account.getTotalAmount(), 0);
    }
	
}
