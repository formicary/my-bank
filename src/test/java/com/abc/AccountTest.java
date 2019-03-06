package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

	private static final double intrestRateBracketChunk = 1000;
	
	private static final double savingsIntrestFirst1000$ = 0.001;
	private static final double savingsIntrestAbove1000$ = 0.002;
	
	private static final double checkingIntrest = 0.001;
	
	private static final double maxiSavingsIntrestNoWithdrawlsWithintenDays = 0.005;
	private static final double maxiSavingsIntrestWithdrawlWithinTenDays = 0.001;
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void Deposit() {
		int depositAmount = 100;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		
		assertEquals("depositAmount(100) has been deposited into account so sum must equal depositAmount(100)"
				,account.sumTransactions(), depositAmount, DOUBLE_DELTA);
	}
	
	@Test
	public void InvalidDepositAmount() {
		int depositAmount = -100;
		Account account = new Account(Account.SAVINGS);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be greater than zero");
		account.deposit(depositAmount);
	}
	
	@Test
	public void Withdraw() {
		int depositAmount = 100;
		double withdrawAmount = 50.0;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		account.withdraw(withdrawAmount);
		
		assertEquals("depositAmount(100) minus withdrawAmount(50) must equal 50"
				,account.sumTransactions(), depositAmount - withdrawAmount, DOUBLE_DELTA);
	}
	
	@Test
	public void InvalidWithdrawAmount() {
		int depositAmount = 100;
		double withdrawAmount = -50.0;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be greater than zero");

		account.withdraw(withdrawAmount);
	}
	
	@Test
	public void WithdrawAmountGreaterThanAccountSum() {
		int depositAmount = 100;
		double withdrawAmount = -500.0;
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("amount must be less than current balance");
		
		account.withdraw(withdrawAmount);
	}
	
	@Test
	public void IntrestEarnedCheckingAccount() {
		int depositAmount = 1500;
		
		Account account = new Account(Account.CHECKING);
		account.deposit(depositAmount);
		
		assertEquals("Interest for checking account must equal checkingInterest"
				,account.interestEarned()
				,depositAmount*account.calculateAccruedIntrestRate(checkingIntrest)
				,DOUBLE_DELTA);}

	
	@Test
	public void IntrestEarnedSavingsAccountUnder1000() {
		int depositAmount = 150;
		
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		assertEquals("Interest for a savings account for a balance of 1000$ or under must equal savingInterestFirst1000$"
				,account.interestEarned()
				,depositAmount*account.calculateAccruedIntrestRate(savingsIntrestFirst1000$)
				,DOUBLE_DELTA);}
	
	@Test
	public void IntrestEarnedSavingsAccountOver1000() {
		int depositAmount = 1500;
		
		Account account = new Account(Account.SAVINGS);
		account.deposit(depositAmount);
		
		assertEquals("Interest for a savings account for a balance over 1000$ must equal savingInterestAbove1000$"
				,account.interestEarned()
				,intrestRateBracketChunk*0.001 + depositAmount*account.calculateAccruedIntrestRate(savingsIntrestAbove1000$)
				,DOUBLE_DELTA);
	}
	
	@Test
	public void IntrestEarnedMaxiSavingsAccount_NoWithdrawlsWithinTenDays() {
		int depositAmount = 1500;
		Date transactionDate = new GregorianCalendar(2018, 0, 10).getTime();
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.transactions.add(new Transaction(depositAmount, transactionDate));
	
		assertEquals("Interest for a maxi savings account without any withdrawls within ten days must equal maxiSavingsIntrestNoWithdrawlsWithintenDays"
				,account.interestEarned()
				,depositAmount*account.calculateAccruedIntrestRate(maxiSavingsIntrestNoWithdrawlsWithintenDays)
				,DOUBLE_DELTA);
	}
	
	@Test
	public void IntrestEarnedMaxiSavingsAccount_WithdrawlWithinTenDays() {
		int depositAmount = 1500;
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.deposit(depositAmount);
		
		assertEquals("Interest for a maxi savings account with withdrawls within ten days must equal maxiSavingsIntrestWithdrawlWithinTenDays"
				,account.interestEarned()
				,depositAmount*account.calculateAccruedIntrestRate(maxiSavingsIntrestWithdrawlWithinTenDays)
				,DOUBLE_DELTA);
	}
	
	@Test
	public void SumTransactions() {
		int DepositAmount1 = 100;
		int DepositAmount2 = 200;
		int withdrawAmount = 50;
		Account account = new Account(Account.SAVINGS);
		account.deposit(DepositAmount1);
		account.deposit(DepositAmount2);
		account.withdraw(withdrawAmount);
		
		
		assertEquals("Sum of account transactions must equal above transactions (100 + 200 - 50)"
				,account.sumTransactions()
				,DepositAmount1 + DepositAmount2 - withdrawAmount
				,DOUBLE_DELTA);
	}

	@Test
	public void checkNoWithdrawlsWithinTenDays() {
		int depositAmount = 1500;
		Date transactionDate = new GregorianCalendar(2018, 0, 10).getTime();
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.transactions.add(new Transaction(depositAmount, transactionDate));
	
		assertTrue("No withdrawls within ten days so must return true"
				,account.checkNoWithdrawlsWithinTenDays());
	}
	
	@Test
	public void checkWithdrawlWithinTenDays() {
		int depositAmount = 1500;
		
		Account account = new Account(Account.MAXI_SAVINGS);
		account.deposit(depositAmount);
		
		assertTrue("Withdrawls within ten days so must return false"
				,!account.checkNoWithdrawlsWithinTenDays());
	}
	
	@Test
	public void accruedIntrest() {
		Long DayInMs = (long) 8.64e+7;
    	double dailyIntrestRate = checkingIntrest / 365;
    	Date dateNow = DateProvider.getInstance().now(); 
		Date transactionDate = new GregorianCalendar(2018, 0, 10).getTime();
		
		int depositAmount = 1500;
		Account account = new Account(Account.CHECKING);
		
		account.transactions.add(new Transaction(depositAmount, transactionDate));

    	Long timeBetweenTransactionsMs = Math.abs(dateNow.getTime() - transactionDate.getTime());
    	int daysofAccruedIntrest = (int) (timeBetweenTransactionsMs / DayInMs);
		
		assertEquals("Accrued intrest must equal the daily interest rate multiplied by the number of days since first transaction"
				,account.calculateAccruedIntrestRate(checkingIntrest)
				,dailyIntrestRate*(daysofAccruedIntrest%365)
				, DOUBLE_DELTA);
	}
	
	@Test
	public void accruedIntrestPastOneYear() {
		Long DayInMs = (long) 8.64e+7;
		double dailyIntrestRate = checkingIntrest / 365;
		Date dateNow = DateProvider.getInstance().now(); 
		Date transactionDate = new GregorianCalendar(2017, 0, 10).getTime();
		
		int depositAmount = 1500;
		Account account = new Account(Account.CHECKING);
		
		account.transactions.add(new Transaction(depositAmount, transactionDate));
		
		Long timeBetweenTransactionsMs = Math.abs(dateNow.getTime() - transactionDate.getTime());
		int daysofAccruedIntrest = (int) (timeBetweenTransactionsMs / DayInMs);
		
		assertEquals("Accrued intrest restarts yearly so must equal the modulus of the days since first transaction divided by the number of days in the year"
				,account.calculateAccruedIntrestRate(checkingIntrest)
				,dailyIntrestRate*(daysofAccruedIntrest%365)
				, DOUBLE_DELTA);
	}
	
}
