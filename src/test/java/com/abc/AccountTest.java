package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {
	
	private static final double intrestRateBracketChunk = 1000;
	
	private static final double savingsIntrestFirst1000$ = 0.001;
	private static final double savingsIntrestAbove1000$ = 0.002;
	
	private static final double checkingIntrest = 0.001;
	
	private static final double maxiSavingsIntrestNoWithdrawlsWithintenDays = 0.05;
	private static final double maxiSavingsIntrestWithdrawlWithinTenDays = 0.001;
	
	private static final int depositAmount = 100;
	private static final int largeDepositAmount = 1500;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void Deposit() {
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		
		assertEquals("depositAmount(100) has been deposited into account so sum must equal depositAmount(100)"
				,account.sumTransactions(), depositAmount, ConstantsTest.DOUBLE_DELTA);
	}
	
	@Test
	public void InvalidDepositAmount() {
		Account account = new Account(Account.SAVINGS);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be greater than zero");
		account.deposit(-depositAmount);
	}
	
	@Test
	public void Withdraw() {
		double withdrawAmount = 50.0;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		account.withdraw(withdrawAmount);
		
		assertEquals("depositAmount(100) minus withdrawAmount(50) must equal 50"
				,account.sumTransactions(), depositAmount - withdrawAmount, ConstantsTest.DOUBLE_DELTA);
	}
	
	@Test
	public void InvalidWithdrawAmount() {
		double withdrawAmount = -50.0;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be greater than zero");

		account.withdraw(withdrawAmount);
	}
	
	@Test
	public void WithdrawAmountGreaterThanAccountSum() {
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be less than current balance");
		
		account.withdraw(2*depositAmount);
	}
	
	@Test
	public void intrestEarnedCheckingAccount() {		
		Account account = new Account(Account.CHECKING);
		account.deposit(depositAmount);
		
		assertEquals("Interest for checking account must equal checkingInterest"
				,account.interestEarned()
				,depositAmount*account.calculateAccruedIntrestRate(checkingIntrest)
				,ConstantsTest.DOUBLE_DELTA);}

	
	@Test
	public void intrestEarnedSavingsAccountUnder1000() {		
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		assertEquals("Interest for a savings account for a balance of 1000$ or under must equal savingInterestFirst1000$"
				,account.interestEarned()
				,depositAmount*account.calculateAccruedIntrestRate(savingsIntrestFirst1000$)
				,ConstantsTest.DOUBLE_DELTA);}
	
	@Test
	public void intrestEarnedSavingsAccountOver1000() {		
		Account account = new Account(Account.SAVINGS);
		account.deposit(largeDepositAmount);
		
		assertEquals("Interest for a savings account for a balance over 1000$ must equal savingInterestAbove1000$"
				,account.interestEarned()
				,intrestRateBracketChunk*0.001 + largeDepositAmount*account.calculateAccruedIntrestRate(savingsIntrestAbove1000$)
				,ConstantsTest.DOUBLE_DELTA);
	}
	
	@Test
	public void intrestEarnedMaxiSavingsAccount_NoWithdrawlsWithinTenDays() {
		Date dateFifteenDaysAgo = new GregorianCalendar(Calendar.DAY_OF_YEAR,Calendar.DAY_OF_MONTH, -15).getTime();
		Date dateTwentyDaysAgo = new GregorianCalendar(Calendar.DAY_OF_YEAR,Calendar.DAY_OF_MONTH, -15).getTime();
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.transactions.add(new Transaction(depositAmount, dateTwentyDaysAgo));
		account.transactions.add(new Transaction(-(1/2)*depositAmount, dateFifteenDaysAgo));
	
		assertEquals("Interest for a maxi savings account without any withdrawls within ten days must equal the sum of transactions multiplied by maxiSavingsIntrestNoWithdrawlsWithintenDays"
				,account.interestEarned()
				,(depositAmount-(1/2)*depositAmount)*account.calculateAccruedIntrestRate(maxiSavingsIntrestNoWithdrawlsWithintenDays)
				,ConstantsTest.DOUBLE_DELTA);
	}
	
	@Test
	public void intrestEarnedMaxiSavingsAccount_WithdrawlWithinTenDays() {	
		Date dateFiveDaysAgo = new GregorianCalendar(Calendar.DAY_OF_YEAR,Calendar.DAY_OF_MONTH, -5).getTime();
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.transactions.add(new Transaction(depositAmount, dateFiveDaysAgo));
		
		int withdrawlAmount = 50;
		account.withdraw(withdrawlAmount);
		
		assertEquals("Interest for a maxi savings account with withdrawls within ten days must equal the sum of transactions multiplied by maxiSavingsIntrestWithdrawlWithinTenDays"
				,account.interestEarned()
				,(depositAmount-withdrawlAmount)*account.calculateAccruedIntrestRate(maxiSavingsIntrestWithdrawlWithinTenDays)
				,ConstantsTest.DOUBLE_DELTA);
	}
	
	@Test
	public void sumTransactions() {
		int withdrawAmount = 50;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		account.deposit(depositAmount);
		account.withdraw(withdrawAmount);
		
		
		assertEquals("Sum of account transactions must equal above transactions (100 + 100 - 50)"
				,account.sumTransactions()
				,depositAmount + depositAmount - withdrawAmount
				,ConstantsTest.DOUBLE_DELTA);
	}

	@Test
	public void checkNoWithdrawlsWithinTenDays() {
		Date transactionDate = new GregorianCalendar(2018, 0, 10).getTime();
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.transactions.add(new Transaction(depositAmount, transactionDate));
	
		assertTrue("No withdrawls within ten days so must return true"
				,account.checkNoWithdrawlsWithinTenDays());
	}
	
	@Test
	public void checkWithdrawlWithinTenDays() {
		Account account = new Account(Account.MAXI_SAVINGS);
		account.deposit(depositAmount);
		
		assertTrue("Withdrawls within ten days so must return false"
				,!account.checkNoWithdrawlsWithinTenDays());
	}
	
	@Test
	public void checkWithdrawlWithinTenDays_NoTransactions() {
		Account account = new Account(Account.MAXI_SAVINGS);
		
		assertTrue("No transactions so must return false"
				,!account.checkNoWithdrawlsWithinTenDays());
	}
	
	@Test
	public void accruedIntrest() {
		Long dayInMs = (long) 8.64e+7;
    	double dailyIntrestRate = checkingIntrest / 365;
    	Date dateNow = DateProvider.getInstance().now(); 
		Date transactionDate = new GregorianCalendar(2018, 0, 10).getTime();
		
		Account account = new Account(Account.CHECKING);
		
		account.transactions.add(new Transaction(depositAmount, transactionDate));

    	Long timeBetweenTransactionsMs = Math.abs(dateNow.getTime() - transactionDate.getTime());
    	int daysofAccruedIntrest = (int) (timeBetweenTransactionsMs / dayInMs);
		
		assertEquals("Accrued intrest must equal the daily interest rate multiplied by the number of days since first transaction"
				,account.calculateAccruedIntrestRate(checkingIntrest)
				,dailyIntrestRate*(daysofAccruedIntrest%365)
				, ConstantsTest.DOUBLE_DELTA);
	}
	
	@Test
	public void accruedIntrestPastOneYear() {
		Long dayInMs = (long) 8.64e+7;
		double dailyIntrestRate = checkingIntrest / 365;
		Date dateNow = DateProvider.getInstance().now(); 
		Date transactionDate = new GregorianCalendar(2017, 0, 10).getTime();
		
		Account account = new Account(Account.CHECKING);
		
		account.transactions.add(new Transaction(depositAmount, transactionDate));
		
		Long timeBetweenTransactionsMs = Math.abs(dateNow.getTime() - transactionDate.getTime());
		int daysofAccruedIntrest = (int) (timeBetweenTransactionsMs / dayInMs);
		
		assertEquals("Accrued intrest restarts yearly so must equal the modulus of the days since first transaction by the number of days in the year"
				,account.calculateAccruedIntrestRate(checkingIntrest)
				,dailyIntrestRate*(daysofAccruedIntrest%365)
				, ConstantsTest.DOUBLE_DELTA);
	}
	
}
