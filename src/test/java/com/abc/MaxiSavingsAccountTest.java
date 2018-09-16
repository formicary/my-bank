package com.abc;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class MaxiSavingsAccountTest {

	@Test
	public void interestEarnedWithdrawalMadeinExactlyTenDaysThenReturnsInterest() {
		// Given
		final LocalDate tenthMarch = LocalDate.of(2018, 3, 10);
		final LocalDate twentryFirstMarch = LocalDate.of(2018, 3, 20);
		final ICurrentTimeProvider transactionTimeProvider = new StubCurrentTimeProvider(tenthMarch);
		final ICurrentTimeProvider interestedEarchedTimeProvider = new StubCurrentTimeProvider(twentryFirstMarch);
		
		// When
		final MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount("ABC");
		maxiSavingsAccount.setCurrentTimeProvider(transactionTimeProvider);
		maxiSavingsAccount.deposit(100.0d);
		maxiSavingsAccount.setCurrentTimeProvider(interestedEarchedTimeProvider);
		final double actual = maxiSavingsAccount.interestEarned();
		
		// Then
		assertEquals(0.027d, actual, 0.001d);
	}
	
	@Test
	public void interestEarnedNoWithdrawalMadeWithinTenDaysThenReturnsInterest() {
		// Given
		final LocalDate tenthMarch = LocalDate.of(2018, 3, 10);
		final LocalDate twentryFirstMarch = LocalDate.of(2018, 3, 21);
		final ICurrentTimeProvider transactionTimeProvider = new StubCurrentTimeProvider(tenthMarch);
		final ICurrentTimeProvider interestedEarchedTimeProvider = new StubCurrentTimeProvider(twentryFirstMarch);
		
		// When
		final MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount("ABC");
		maxiSavingsAccount.setCurrentTimeProvider(transactionTimeProvider);
		maxiSavingsAccount.deposit(100.0d);
		maxiSavingsAccount.setCurrentTimeProvider(interestedEarchedTimeProvider);
		final double actual = maxiSavingsAccount.interestEarned();
		
		// Then
		assertEquals(1.337d, actual, 0.001d);
	}
	
	@Test
	public void interestEarnedWithdrawalMadeWithinTenDaysThenReturnsInterest() {
		// Given
		final LocalDate tenthMarch = LocalDate.of(2018, 3, 10);
		final LocalDate twentryFirstMarch = LocalDate.of(2018, 3, 19);
		final ICurrentTimeProvider transactionTimeProvider = new StubCurrentTimeProvider(tenthMarch);
		final ICurrentTimeProvider interestedEarchedTimeProvider = new StubCurrentTimeProvider(twentryFirstMarch);
		
		// When
		final MaxiSavingsAccount maxiSavingsAccount = new MaxiSavingsAccount("ABC");
		maxiSavingsAccount.setCurrentTimeProvider(transactionTimeProvider);
		maxiSavingsAccount.deposit(100.0d);
		maxiSavingsAccount.setCurrentTimeProvider(interestedEarchedTimeProvider);
		final double actual = maxiSavingsAccount.interestEarned();
		
		// Then
		assertEquals(0.027d, actual, 0.001d);
	}
}
