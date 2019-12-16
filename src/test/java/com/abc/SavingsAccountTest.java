package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final double INTEREST_RATE = 0.001 / 365;
    private static final double PRO_INTEREST_RATE = 0.002 / 365;
    
    @Test
    public void checkCorrectType() {
    	SavingsAccount savingsAccount = new SavingsAccount();
    	assertEquals(AccountType.SAVINGS, savingsAccount.getType());
    }
   
    
    @Test
    public void checkCorrectBalanceDeposit() {
    	SavingsAccount savingsAccount = new SavingsAccount();
    	savingsAccount.deposit(1000.0);
    	assertEquals(1000.0, savingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCorrectBalanceDepositThenWithdraw() {
    	SavingsAccount savingsAccount = new SavingsAccount();
    	savingsAccount.deposit(1000.0);
    	savingsAccount.withdraw(200);
    	assertEquals(800.0, savingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkBalanceAfterTransfer() {
    	SavingsAccount savingsAccount = new SavingsAccount();
    	SavingsAccount savingsAccount2 = new SavingsAccount();
    	savingsAccount.deposit(1000.0);
    	savingsAccount.transfer(200.0, savingsAccount2);
    	assertEquals(800.0, savingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkBalanceAfterReceiving() {
    	SavingsAccount savingsAccount = new SavingsAccount();
    	SavingsAccount savingsAccount2 = new SavingsAccount();
    	savingsAccount2.deposit(1000.0);
    	savingsAccount2.transfer(200.0, savingsAccount);
    	assertEquals(200.0, savingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkStatement() {
    	SavingsAccount savingsAccount = new SavingsAccount();
    	SavingsAccount savingsAccount2 = new SavingsAccount();
    	savingsAccount.deposit(1000.0);
    	savingsAccount.withdraw(200);
    	savingsAccount.transfer(300, savingsAccount2);
    	savingsAccount2.transfer(120, savingsAccount);
    	String statement = "Account Type: SAVINGS\n" + 
    			"DEPOSIT: 1000.0\n" + 
    			"WITHDRAW: 200.0\n" + 
    			"TRANSFER: 300.0 to: SAVINGS\n" + 
    			"RECEIVE: 120.0 from: SAVINGS\n" + 
    			"Current balance: $620.00";
    	assertEquals(statement, savingsAccount.getStatement());
    }
    
    @Test
    public void checkNormalInterestGain() {
    	SavingsAccount SavingsAccount = new SavingsAccount();
    	SavingsAccount.deposit(900.0);
    	
    	SavingsAccount.gainInterest();
    	
    	double interestGain = 900.0 * INTEREST_RATE;
    	
    	assertEquals(interestGain, SavingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkProInterestGain() {
    	SavingsAccount SavingsAccount = new SavingsAccount();
    	SavingsAccount.deposit(1100.0);
    	
    	SavingsAccount.gainInterest();
    	
    	double interestGain = 1100.0 * PRO_INTEREST_RATE;
    	
    	assertEquals(interestGain, SavingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkNormalInterestBalance() {
    	SavingsAccount SavingsAccount = new SavingsAccount();
    	SavingsAccount.deposit(900.0);
    	SavingsAccount.gainInterest();
    	
    	double interestGain = 900.0 * INTEREST_RATE;
    	double balance = 900.0 + interestGain;
    	assertEquals(balance, SavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkProInterestBalance() {
    	SavingsAccount SavingsAccount = new SavingsAccount();
    	SavingsAccount.deposit(1100.0);
    	SavingsAccount.gainInterest();
    	
    	double interestGain = 1100.0 * PRO_INTEREST_RATE;
    	double balance = 1100.0 + interestGain;
    	assertEquals(balance, SavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCompoundNormalInterest() {
    	SavingsAccount SavingsAccount = new SavingsAccount();
    	SavingsAccount.deposit(900.0);
    	SavingsAccount.gainInterest();
    	SavingsAccount.gainInterest();
    	SavingsAccount.gainInterest();
    	
    	double balance = 900;
    	double interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	
    	assertEquals(balance, SavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCompoundProInterest() {
    	SavingsAccount SavingsAccount = new SavingsAccount();
    	SavingsAccount.deposit(1100.0);
    	SavingsAccount.gainInterest();
    	SavingsAccount.gainInterest();
    	SavingsAccount.gainInterest();
    	
    	double balance = 1100;
    	double interestGain = balance * PRO_INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * PRO_INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * PRO_INTEREST_RATE;
    	balance += interestGain;
    	
    	assertEquals(balance, SavingsAccount.getBalance(), DOUBLE_DELTA);
    }


}
