package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.lang.Math;

public class AccountTest {

	Account checkingAccount = new Account(Account.Type.CHECKING);
    Account savingsAccount = new Account(Account.Type.SAVINGS);
    Account maxiSavingsAccount = new Account(Account.Type.MAXI_SAVINGS);

	@Test //Test getAccountType for Checking Account
	public void getAccountType_CheckingAccount_CHECKING() {
        assertEquals(Account.Type.CHECKING, checkingAccount.getAccountType());
	}
	
	@Test //Test getAccountType for Savings Account
	public void getAccountType_SavingsAccount_SAVINGS() {
        assertEquals(Account.Type.SAVINGS, savingsAccount.getAccountType());
	}
	
	@Test //Test getAccountType for Maxi_Savings Account
	public void getAccountType_MaxiSavingsAccount_MAXI_SAVINGS() {
        assertEquals(Account.Type.MAXI_SAVINGS, maxiSavingsAccount.getAccountType());
	}
	
	@Test //Test Deposit of a positive amount
	public void deposit_PositiveAmount_NoExceptionThrown() {
		checkingAccount.deposit(300.0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	//Test Deposit of a positive amount
	public void deposit_NegativeAmount_ExceptionThrown() {
		checkingAccount.deposit(-300.0);
	}
	
	@Test //Test Deposit of a positive amount
	public void withdraw_PositiveAmount_NoExceptionThrown() {
		checkingAccount.withdraw(300.0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	//Test Deposit of a positive amount
	public void withdraw_NegativeAmount_ExceptionThrown() {
		checkingAccount.withdraw(-300.0);
	}
	
	@Test //Test Account Statement for no transactions
	public void getAccountStatement_NoTransactions_EmptyBalance() {
		assertEquals("", checkingAccount.getAccountStatement());
	}
	
	@Test //Test Account Statement for 2 transactions
	public void getAccountStatement_2Transactions_AllTransactionsReturned() {
		checkingAccount.deposit(300.0);
        checkingAccount.withdraw(100.0);
		assertEquals("  deposit $300.00\n" +
                "  withdrawal $100.00\n" +
                "  interest $0.00\n", checkingAccount.getAccountStatement());
	}
	
	@Test //Test Sum of Transactions for no transactions
	public void sumTransactions_NoTransactions_ZeroReturned() {
		assertEquals(0.00, checkingAccount.sumTransactions(), 0.0);
	}
	
	@Test //Test Sum of Transactions for no transactions
	public void sumTransactions_PositiveTotal_TotalReturned() {
		checkingAccount.deposit(300.0);
		checkingAccount.deposit(200.0);
		checkingAccount.withdraw(354.0);
		assertEquals(146.00, checkingAccount.sumTransactions(), 0.0);
	}
	
	@Test //Test Sum of Transactions for no transactions
	public void sumTransactions_NegativeTotal_TotalReturned() {
		checkingAccount.deposit(300.0);
		checkingAccount.withdraw(200.0);
		checkingAccount.withdraw(104.0);
		assertEquals(-4.00, checkingAccount.sumTransactions(), 0.0);
	}
	
	@Test //Test Interest Accrued for Balance of 0 in Checking Account
	public void totalInterest_NoBalanceChecking_NoInterestAccrued() {
		assertEquals(0.0, checkingAccount.totalInterest(), 0.0);
	}
	
	@Test //Test Interest Accrued for Negative Balance in Checking Account
	public void totalInterest_NegBalanceChecking_NoInterestAccrued() {
		checkingAccount.withdraw(200.0);
		assertEquals(0.0, checkingAccount.totalInterest(), 0.0);
	}
	
	@Test //Test Interest Accrued for Positive Balance in Checking Account
	public void totalInterest_PosBalanceChecking_InterestAccrued() {
		//Add transaction to account on given date
		LocalDate date = LocalDate.of(2017,06,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		checkingAccount.addTransaction(deposit);
		
		//Use date difference (in days) to calculate  expected interest
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime transactionDate = LocalDateTime.from(date.atStartOfDay());
		double dateDiff = transactionDate.until(today, ChronoUnit.DAYS);
		double expectedInterest = 200.0*Math.pow((1+(0.001/365.0)), dateDiff) - 200.0;
		
		//Compare calculated interest and result of totalInterest method
		assertEquals(expectedInterest, checkingAccount.totalInterest(), 0.000001);
	}
	
	@Test //Test Interest Accrued for Balance of 0 in Savings Account
	public void totalInterest_NoBalanceSavings_NoInterestAccrued() {
		assertEquals(0.0, savingsAccount.totalInterest(), 0.0);
	}
	
	@Test //Test Interest Accrued for Negative Balance in Savings Account
	public void totalInterest_NegBalanceSavings_NoInterestAccrued() {
		LocalDate date = LocalDate.of(2016,05,04);
		Transaction withdrawal = new Transaction(-200.0, date.atStartOfDay());
		savingsAccount.addTransaction(withdrawal);
		assertEquals(0.0, savingsAccount.totalInterest(), 0.0);
	}
	
	@Test //Test Interest Accrued for Balance under 1000 in Savings Account
	public void totalInterest_PosBalanceSavingsUnder1000_ReturnInterestAccrued() {
		//Add transaction to account on given date
		LocalDate date = LocalDate.of(2017,06,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		savingsAccount.addTransaction(deposit);
		
		//Use date difference (in days) to approximate expected interest
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime transactionDate = LocalDateTime.from(date.atStartOfDay());
		double dateDiff = transactionDate.until(today, ChronoUnit.DAYS);
		double expectedInterest = 200.0*Math.pow((1+(0.001/365.0)), dateDiff) - 200.0;
		
		//Compare calculated interest and result of totalInterest method
		assertEquals(expectedInterest, savingsAccount.totalInterest(), 0.0000001);
	}
	
	@Test //Test Interest Accrued for Balance over 1000 in Savings Account
	public void totalInterest_PosBalanceSavingsOver1000_ReturnInterestAccrued() {
		//Add transaction to account on given date
		LocalDate date = LocalDate.of(2018,06,01);
		Transaction deposit = new Transaction(1200.0, date.atStartOfDay());
		savingsAccount.addTransaction(deposit);
		
		//Use date difference (in days) to approximate expected interest
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime transactionDate = LocalDateTime.from(date.atStartOfDay());
		double dateDiff = transactionDate.until(today, ChronoUnit.DAYS);
		double expectedInterest = 1000.0*Math.pow((1+(0.001/365.0)), dateDiff) - 1000.0;
		expectedInterest += 200.0*Math.pow((1+(0.002/365.0)), dateDiff) - 200.0;
		
		//Compare calculated interest and result of totalInterest method
		assertEquals(expectedInterest, savingsAccount.totalInterest(), 0.0001);
	}
	
	@Test //Test Interest Accrued for Balance of 0 in Maxi_Savings Account
	public void totalInterest_NoBalanceMaxiSavings_NoInterestAccrued() {
		assertEquals(0.0, maxiSavingsAccount.totalInterest(), 0.0);
	}
	
	@Test //Test Interest Accrued for Negative Balance in Maxi_Savings Account
	public void totalInterest_NegBalanceMaxiSavings_NoInterestAccrued() {
		LocalDate date = LocalDate.of(2016,05,04);
		Transaction withdrawal = new Transaction(-200.0, date.atStartOfDay());
		savingsAccount.addTransaction(withdrawal);
		assertEquals(0.0, maxiSavingsAccount.totalInterest(), 0.0);
	}
	
	@Test //Test Interest Accrued for Balance under $1000 in Maxi_Savings Account
	public void totalInterest_PosBalanceMaxiSavingsUnder1000_NoInterestAccrued() {
		LocalDate date = LocalDate.of(2018,05,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		
		//Use date difference (in days) to calculate expected interest
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime transactionDate = LocalDateTime.from(date.atStartOfDay());
		double dateDiff = transactionDate.until(today, ChronoUnit.DAYS);
		double expectedInterest = 200.0*Math.pow((1+(0.02/365.0)), dateDiff) - 200.0;
		maxiSavingsAccount.addTransaction(deposit);
		assertEquals(expectedInterest, maxiSavingsAccount.totalInterest(), 0.0000001);
	}
	
	@Test //Test Interest Accrued for Balance under $2000 in Maxi_Savings Account
	public void totalInterest_PosBalanceMaxiSavingsUnder2000_NoInterestAccrued() {
		LocalDate date = LocalDate.of(2018,05,01);
		Transaction deposit = new Transaction(1200.0, date.atStartOfDay());
		
		//Use date difference (in days) to approximate expected interest
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime transactionDate = LocalDateTime.from(date.atStartOfDay());
		double dateDiff = transactionDate.until(today, ChronoUnit.DAYS);
		double expectedInterest = 1000.0*Math.pow((1+(0.02/365.0)), dateDiff) - 1000.0;
		expectedInterest += 200.0*Math.pow((1+(0.05/365.0)), dateDiff) - 200.0;
		maxiSavingsAccount.addTransaction(deposit);
		assertEquals(expectedInterest, maxiSavingsAccount.totalInterest(), 0.01);
	}
	
	@Test //Test Interest Accrued for Balance over $2000 in Maxi_Savings Account
	public void totalInterest_PosBalanceMaxiSavingsOver2000_NoInterestAccrued() {
		LocalDate date = LocalDate.of(2018,05,01);
		Transaction deposit = new Transaction(2200.0, date.atStartOfDay());
		
		//Use date difference (in days) to approximate expected interest
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime transactionDate = LocalDateTime.from(date.atStartOfDay());
		double dateDiff = transactionDate.until(today, ChronoUnit.DAYS);
		double expectedInterest = 1000.0*Math.pow((1+(0.02/365.0)), dateDiff) - 1000.0;
		expectedInterest += 1000.0*Math.pow((1+(0.05/365.0)), dateDiff) - 1000.0;
		expectedInterest += 200.0*Math.pow((1+(0.1/365.0)), dateDiff) - 200.0;
		maxiSavingsAccount.addTransaction(deposit);
		assertEquals(expectedInterest, maxiSavingsAccount.totalInterest(), 0.1);
	}
	
}
