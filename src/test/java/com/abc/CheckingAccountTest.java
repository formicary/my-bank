package com.abc;

import org.junit.Test;

import com.abc.Account.AccountType;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CheckingAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    private static final double INTEREST_RATE = 0.001 / 365;
    
    @Test
    public void checkCorrectType() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	assertEquals(AccountType.CHECKING, checkingAccount.getType());
    }
    
    @Test
    public void checkCorrectBalanceDeposit() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	assertEquals(1000.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCorrectBalanceDepositThenWithdraw() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	checkingAccount.withdraw(200);
    	assertEquals(800.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkBalanceAfterTransfer() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	CheckingAccount checkingAccount2 = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	checkingAccount.transfer(200.0, checkingAccount2);
    	assertEquals(800.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkBalanceAfterReceiving() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	CheckingAccount checkingAccount2 = new CheckingAccount();
    	checkingAccount2.deposit(1000.0);
    	checkingAccount2.transfer(200.0, checkingAccount);
    	assertEquals(200.0, checkingAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkStatement() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	CheckingAccount checkingAccount2 = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	checkingAccount.withdraw(200);
    	checkingAccount.transfer(300, checkingAccount2);
    	checkingAccount2.transfer(150, checkingAccount);
    	String statement = "Account Type: CHECKING\n" + 
    			"DEPOSIT: 1000.0\n" + 
    			"WITHDRAW: 200.0\n" + 
    			"TRANSFER: 300.0 to: CHECKING\n" + 
    			"RECEIVE: 150.0 from: CHECKING\n" + 
    			"Current balance: $650.00";
    	assertEquals(statement, checkingAccount.getStatement());
    }
    
    
    @Test
    public void checkInterestGain() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	
    	checkingAccount.gainInterest();
    	
    	double interestGain = 1000.0 * INTEREST_RATE;
    	
    	assertEquals(interestGain, checkingAccount.getTotalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkInterestBalance() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	checkingAccount.gainInterest();
    	
    	double interestGain = 1000.0 * INTEREST_RATE;
    	double balance = 1000.0 + interestGain;
    	assertEquals(balance, checkingAccount.getBalance(), DOUBLE_DELTA);
    }
    
    @Test
    public void checkCompoundInterest() {
    	CheckingAccount checkingAccount = new CheckingAccount();
    	checkingAccount.deposit(1000.0);
    	checkingAccount.gainInterest();
    	checkingAccount.gainInterest();
    	checkingAccount.gainInterest();
    	
    	double balance = 1000;
    	double interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	interestGain = balance * INTEREST_RATE;
    	balance += interestGain;
    	
    	assertEquals(balance, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

}
