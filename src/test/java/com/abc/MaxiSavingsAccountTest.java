package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final double INTEREST_RATE = 0.05 / 365;
    private static final double WITHDRAWN_INTEREST_RATE = 0.001 / 365;
    
    @Test
    public void checkCorrectType() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	assertEquals(AccountType.MAXI_SAVINGS, maxiSavingsAccount.getType());
    }
   
    
    @Test
    public void checkCorrectBalanceDeposit() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1000.0);
    	assertEquals(1000.0, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCorrectBalanceDepositThenWithdraw() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1000.0);
    	maxiSavingsAccount.withdraw(200);
    	assertEquals(800.0, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkBalanceAfterTransfer() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	MaxiSavingsAccount maxiSavingsAccount2 = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1000.0);
    	maxiSavingsAccount.transfer(200.0, maxiSavingsAccount2);
    	assertEquals(800.0, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkBalanceAfterReceiving() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	MaxiSavingsAccount maxiSavingsAccount2 = new MaxiSavingsAccount();
    	maxiSavingsAccount2.deposit(1000.0);
    	maxiSavingsAccount2.transfer(200.0, maxiSavingsAccount);
    	assertEquals(200.0, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkStatement() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	MaxiSavingsAccount maxiSavingsAccount2 = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1000.0);
    	maxiSavingsAccount.withdraw(200);
    	maxiSavingsAccount.transfer(300, maxiSavingsAccount2);
    	maxiSavingsAccount2.transfer(130, maxiSavingsAccount);
    	String statement = "Account Type: MAXI_SAVINGS\n" + 
    			"DEPOSIT: 1000.0\n" + 
    			"WITHDRAW: 200.0\n" + 
    			"TRANSFER: 300.0 to: MAXI_SAVINGS\n" + 
    			"RECEIVE: 130.0 from: MAXI_SAVINGS\n" + 
    			"Current balance: $630.00";
    	assertEquals(statement, maxiSavingsAccount.getStatement());
    }
    
    @Test
    public void checkNormalInterestGain() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(900.0);
    	
    	maxiSavingsAccount.gainInterest();
    	
    	double interestGain = 900.0 * INTEREST_RATE;
    	
    	assertEquals(interestGain, maxiSavingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkWithdrawnInterestGain() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1100.0);
    	maxiSavingsAccount.withdraw(300);
    	
    	maxiSavingsAccount.gainInterest();
    	
    	double interestGain = 800.0 * WITHDRAWN_INTEREST_RATE;
    	
    	assertEquals(interestGain, maxiSavingsAccount.getTotalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkNormalInterestBalance() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(900.0);
    	maxiSavingsAccount.gainInterest();
    	
    	double interestGain = 900.0 * INTEREST_RATE;
    	double balance = 900.0 + interestGain;
    	assertEquals(balance, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkWithdrawnInterestBalance() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1100.0);
    	maxiSavingsAccount.withdraw(300);
    	maxiSavingsAccount.gainInterest();
    	
    	double interestGain = 800.0 * WITHDRAWN_INTEREST_RATE;
    	double balance = 800.0 + interestGain;
    	assertEquals(balance, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCompoundNormalInterest() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(900.0);
    	maxiSavingsAccount.gainInterest();
    	maxiSavingsAccount.gainInterest();
    	maxiSavingsAccount.gainInterest();
    	
    	double balance = 900;
    	double interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	
    	assertEquals(balance, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCompoundWithdrawnInterest() {
    	MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount();
    	maxiSavingsAccount.deposit(1100.0);
    	maxiSavingsAccount.withdraw(300);
    	maxiSavingsAccount.gainInterest();
    	maxiSavingsAccount.gainInterest();
    	maxiSavingsAccount.gainInterest();
    	
    	double balance = 800;
    	double interestGain = balance * WITHDRAWN_INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * WITHDRAWN_INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * WITHDRAWN_INTEREST_RATE;
    	balance += interestGain;
    	
    	assertEquals(balance, maxiSavingsAccount.getBalance(), DOUBLE_DELTA);
    }


}
