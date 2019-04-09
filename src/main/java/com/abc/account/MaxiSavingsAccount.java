package com.abc.account;

import java.time.LocalDate;
import java.util.function.Predicate;

import com.abc.DateProvider;
import com.abc.Transaction;

public final class MaxiSavingsAccount extends Account {

	public MaxiSavingsAccount(DateProvider dp) {
		super(dp);
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();

		double interestRate = haveWhithdrawals() ? 0.001 : 0.05;

		if (amount <= 1000)
			return amount * 0.02;
		if (amount <= 2000)
			return 20 + (amount - 1000) * interestRate;
		return 70 + (amount - 2000) * 0.1;

	}

	private boolean haveWhithdrawals() {
		LocalDate tenDaysBefore = LocalDate.now().minusDays(10);
		Predicate<Transaction> isWithinPastTenDays = t -> t.getTransactionDate().isAfter(tenDaysBefore);
		Predicate<Transaction> isWithdrawal = t -> t.amount < 0;
		return transactions.stream().filter(isWithinPastTenDays).anyMatch(isWithdrawal);
	}

	@Override
	public String getStatement() {
		return "Maxi Savings Account\n" + getTransactionSummary();
	}

}
