package com.abc;

import org.junit.Test;

import com.abc.Account.ACCOUNT_TYPE;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountTest {
	
    @Test
    public void testDeposit() {
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        BigDecimal amount = new BigDecimal("100");
        
        account.deposit(amount);
    	
        assertEquals(account.getBalance().compareTo(new BigDecimal("100")), 0);
    }
    
    @Test
    public void testDepositZero() {
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        BigDecimal amount = BigDecimal.ZERO;
        
    	try {
    		account.deposit(amount);	
    	} catch (IllegalArgumentException e){
    		assertEquals(e.getMessage(), "Amount must be greater than zero");
    	}
    }
    
    @Test
    public void testWithdraw() {
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        
        account.deposit(new BigDecimal("500"));
        account.withdraw(new BigDecimal("300"));
    	
        assertEquals(account.getBalance().compareTo(new BigDecimal("200")), 0);
    }
    
    @Test
    public void testWithdrawToZero() {
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        BigDecimal amount = new BigDecimal("500");
        
        account.deposit(amount);
        account.withdraw(amount);
    	
        assertEquals(account.getBalance().compareTo(BigDecimal.ZERO), 0);
    }
    
    @Test
    public void testWithdrawZero() {
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        BigDecimal amount = BigDecimal.ZERO;
        
    	try {
    		account.withdraw(amount);	
    	} catch (IllegalArgumentException e){
    		assertEquals(e.getMessage(), "Amount must be greater than zero");
    	}
    }
    
    @Test
    public void testWithdrawNotEnoughFunds() {
        Account account = new Account(ACCOUNT_TYPE.CHECKING);
        BigDecimal amount = BigDecimal.TEN;
        
    	try {
    		account.withdraw(amount);	
    	} catch (IllegalArgumentException e){
    		assertEquals(e.getMessage(), "Account balance must be greater or equal to the withdrawal amount");
    	}
    }
    
    @Test
    public void testCheckingInterest() {
        BigDecimal interestGained = createAccountAndGetInterest(ACCOUNT_TYPE.CHECKING, 100);
        
    	BigDecimal expectedValue = BigDecimal.valueOf(100 * 0.001);
        assertEquals(interestGained.compareTo(expectedValue), 0);
    }

    @Test
    public void testSavingsInterest() {
        BigDecimal interestGained = createAccountAndGetInterest(ACCOUNT_TYPE.SAVINGS, 1500);
        
    	BigDecimal expectedValue = BigDecimal.valueOf((1000 * 0.001) + (500 * 0.002));
        assertEquals(interestGained.compareTo(expectedValue), 0);
    }

    @Test
    public void testMaxiSavingsInterestNoWithdrawals() {
        BigDecimal interestGained = createAccountAndGetInterest(ACCOUNT_TYPE.MAXI_SAVINGS, 3000);
        
    	BigDecimal expectedValue = BigDecimal.valueOf(3000 * 0.05);
        assertEquals(interestGained.compareTo(expectedValue), 0);
    }
    
    @Test
    public void testMaxiSavingsInterestWithdrawalInLast10Days() {
        Account account = new Account(ACCOUNT_TYPE.MAXI_SAVINGS);
        account.deposit(BigDecimal.valueOf(500));
        account.withdraw(BigDecimal.valueOf(100));
        
        account.accrueInterest();
        
    	BigDecimal expectedValue = BigDecimal.valueOf(400 * 0.001);
        assertEquals(account.getAccruedInterest().compareTo(expectedValue), 0);
    }
    
    @Test
    public void testMaxiSavingsInterestWithdrawalNotInLast10Days() {
        Account account = new Account(ACCOUNT_TYPE.MAXI_SAVINGS);
        account.deposit(BigDecimal.valueOf(500));
        account.withdraw(BigDecimal.valueOf(100));
        
        Transaction transaction = account.getTransactions().get(1);
        LocalDateTime minus15Days = transaction.getTransactionDate().minusDays(15);
        transaction.setTransactionDate(minus15Days);
        
        account.accrueInterest();
        
    	BigDecimal expectedValue = BigDecimal.valueOf(400 * 0.05);
        assertEquals(account.getAccruedInterest().compareTo(expectedValue), 0);
    }
    
    private BigDecimal createAccountAndGetInterest(ACCOUNT_TYPE accountType, double depositAmount) {
        Account account = new Account(accountType);
        account.deposit(BigDecimal.valueOf(depositAmount));
        
        account.accrueInterest();
        
        return account.getAccruedInterest();
    }

}
