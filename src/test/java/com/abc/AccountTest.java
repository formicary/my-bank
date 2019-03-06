package com.abc;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {
	private static final double intrestRateBracketChunk = 1000;
	
	private static final double savingsIntrestFirst1000$ = 0.001;
	private static final double savingsIntrestAbove1000$ = 0.002;
	
	private static final double checkingIntrest = 0.001;
	
	private static final double maxiSavingsIntrestFirst1000$ = 0.02;
	private static final double maxiSavingsIntrestSecond1000$ = 0.05;
	private static final double maxiSavingsIntrestAbove2000$ = 0.1;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testDeposit() {
		int testDepositAmount = 100;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		
		assertTrue(testAccount.sumTransactions() == testDepositAmount);
	}
	
	@Test
	public void testInvalidDepositAmount() {
		int testDepositAmount = -100;
		Account testAccount = new Account(Account.SAVINGS);
		
		exception.expect(IllegalArgumentException.class);
		testAccount.deposit(testDepositAmount);
	}
	
	@Test
	public void testWithdraw() {
		int testDepositAmount = 100;
		double testWithdrawAmount = 50.0;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount);
		testAccount.withdraw(testWithdrawAmount);
		
		assertTrue(testAccount.sumTransactions() == testDepositAmount - testWithdrawAmount);
	}
	
	@Test
	public void testInvalidWithdrawAmount() {
		int testDepositAmount = 100;
		double testWithdrawAmount = -50.0;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		exception.expect(IllegalArgumentException.class);
		
		testAccount.withdraw(testWithdrawAmount);
	}
	
	@Test
	public void testWithdrawAmountGreaterThanAccountSum() {
		int testDepositAmount = 100;
		double testWithdrawAmount = -500.0;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		exception.expect(IllegalArgumentException.class);
		
		testAccount.withdraw(testWithdrawAmount);
	}
	
	@Test
	public void testIntrestEarnedCheckingAccount() {
		int testDepositAmount = 100;
		Account testAccount = new Account(Account.CHECKING);
		testAccount.deposit(testDepositAmount);
		
		assertTrue(testAccount.interestEarned() == testDepositAmount*checkingIntrest);
	}

	
	@Test
	public void testIntrestEarnedSavingsAccountUnder1000() {
		int testDepositAmount = 100;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		assertTrue(testAccount.interestEarned() == testDepositAmount*savingsIntrestFirst1000$);
	}
	
	@Test
	public void testIntrestEarnedSavingsAccountOver1000() {
		int testDepositAmount = 1500;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		assertTrue(testAccount.interestEarned() ==
				intrestRateBracketChunk*savingsIntrestFirst1000$ +
				(testDepositAmount-1000)*savingsIntrestAbove1000$);
	}
	
	@Ignore
	@Test
	public void testIntrestEarnedMaxiSavingsAccountUnder1000() {
		int testDepositAmount = 100;
		Account testAccount = new Account(Account.MAXI_SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		assertTrue(testAccount.interestEarned() == testDepositAmount*maxiSavingsIntrestFirst1000$);
	}
	
	@Ignore
	@Test
	public void testIntrestEarnedMaxiSavingsAccountUnder2000() {
		int testDepositAmount = 1500;
		Account testAccount = new Account(Account.MAXI_SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		assertTrue(testAccount.interestEarned() ==
				intrestRateBracketChunk*maxiSavingsIntrestFirst1000$ +
				(testDepositAmount - 1000)*maxiSavingsIntrestSecond1000$);
	}
	
	@Ignore
	@Test
	public void testIntrestEarnedMaxiSavingsAccountAbove2000() {
		int testDepositAmount = 2500;
		Account testAccount = new Account(Account.MAXI_SAVINGS);
		testAccount.deposit(testDepositAmount);
		
		assertTrue(testAccount.interestEarned() ==
				intrestRateBracketChunk*maxiSavingsIntrestFirst1000$ +
				intrestRateBracketChunk*maxiSavingsIntrestSecond1000$ +
				  (testDepositAmount-2000)*maxiSavingsIntrestAbove2000$);
	}
	
	@Test
	public void testSumTransactions() {
		int testDepositAmount1 = 100;
		int testDepositAmount2 = 200;
		Account testAccount = new Account(Account.SAVINGS);
		testAccount.deposit(testDepositAmount1);
		testAccount.deposit(testDepositAmount2);
		
		
		assertTrue(testAccount.sumTransactions() == testDepositAmount1 + testDepositAmount2);
	}

	
}
