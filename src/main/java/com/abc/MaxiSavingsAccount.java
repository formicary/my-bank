package com.abc;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class MaxiSavingsAccount extends Account {

	private long daysWithoutWithdrawal;

	public MaxiSavingsAccount(int accountNumber) {
		super(accountNumber);

		setAccountName("Maxi-Savings Account");
		// Initial interest rate of 5%
		setInterestRate(5);

		daysWithoutWithdrawal = 10;
	}

	@Override
	public void deposit(BigDecimal amount) {
		setBalance(getBalance().add(amount));
		getTransactions().add(new Transaction(amount));
	}

	@Override
	public boolean withdraw(BigDecimal amount) {
		// Check if the account has enough money to withdraw
		if (getBalance().subtract(amount).compareTo(BigDecimal.ZERO) < 0)
			return false;

		setBalance(getBalance().subtract(amount));
		getTransactions().add(new Transaction(amount.negate()));

		// Reset days without a withdrawal number
		daysWithoutWithdrawal = 0;
		// Therefore, set the rate as 1%
		setInterestRate(1);

		return true;
	}

	@Override
	public void dailyTasks() {
		// Find the number of days that have passed since a withdrawal
		daysWithoutWithdrawal = getDateCreated().until(DateProvider.getInstance().getCurrentDate(), ChronoUnit.DAYS);

		// If more than ten days have gone past without a withdrawal, then set the
		// interest rate as 5% again
		if (daysWithoutWithdrawal >= 10 && getInterestRate() == 1) {
			setInterestRate(5);
		}

		setBalance(getBalance().add(compoundInterest(getBalance())));
	}

	public long getDaysWithoutWithdrawal() {
		return daysWithoutWithdrawal;
	}

	// Created for JUnit testing purposes
	public void setDaysWithoutWithdrawal(int days) {
		daysWithoutWithdrawal = days;
	}

}
