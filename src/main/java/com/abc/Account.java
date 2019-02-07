package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	private static int accountCounter = 0;
	private final int accountId;
	private final int accountType;
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.accountId = ++accountCounter;
	}

	public void accountDeposit(BigDecimal amount) {
		if (amount.compareTo(new BigDecimal("0.00")) == -1) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount.setScale(2, RoundingMode.HALF_EVEN)));
		}
	}

	public void accountWithdraw(BigDecimal amount) {
		if ((amount.compareTo(new BigDecimal("0.00")) == -1)) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount.negate().setScale(2, RoundingMode.HALF_EVEN)));
		}
	}

	public BigDecimal interestEarnedPerAnnum() {
		BigDecimal balance = returnAccountBalance();
		switch (accountType) {
		case SAVINGS:
			BigDecimal totalInterest;
			if (balance.compareTo(new BigDecimal("1000.00")) == -1) {
				totalInterest = balance.multiply(new BigDecimal("0.001"));
				return totalInterest.setScale(2, RoundingMode.HALF_EVEN);
			} else {
				BigDecimal newRateBalance = balance.subtract(new BigDecimal("1000.00"));
				BigDecimal interestFromNewRate = newRateBalance.multiply(new BigDecimal("0.002"));
				totalInterest = interestFromNewRate.add(new BigDecimal("1.00"));
				return totalInterest.setScale(2, RoundingMode.HALF_EVEN);
			}
		case MAXI_SAVINGS:
			if (balance.compareTo(new BigDecimal("1000.00")) == -1) {
				totalInterest = balance.multiply(new BigDecimal("0.02"));
				return totalInterest.setScale(2, RoundingMode.HALF_EVEN);

			} else if (balance.compareTo(new BigDecimal("2000.00")) == -1) {
				BigDecimal newRateBalance = balance.subtract(new BigDecimal("1000.00"));
				BigDecimal interestFromNewRate = newRateBalance.multiply(new BigDecimal("0.05"));
				totalInterest = interestFromNewRate.add(new BigDecimal("20.00"));
				return totalInterest.setScale(2, RoundingMode.HALF_EVEN);

			} else {
				
				BigDecimal newRateBalance = balance.subtract(new BigDecimal("2000"));
				BigDecimal interestFromNewRate = newRateBalance.multiply(new BigDecimal("0.1"));
				totalInterest = interestFromNewRate.add(new BigDecimal("70.00"));
				return totalInterest.setScale(2, RoundingMode.HALF_EVEN);
			}
		default:
			totalInterest = balance.multiply(new BigDecimal("0.001"));
			return totalInterest.setScale(2, RoundingMode.HALF_EVEN);
		}
	}

	BigDecimal returnAccountBalance() {
		BigDecimal balance = new BigDecimal("0.00");
		for (Transaction t : transactions)
			balance = balance.add(t.getAmount());

		return balance.setScale(2, RoundingMode.HALF_EVEN);
	}

	public int getAccountType() {
		return accountType;
	}

	public int getAccountId() {
		return accountId;
	}

}
