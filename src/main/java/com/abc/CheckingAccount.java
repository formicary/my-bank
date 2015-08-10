package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
	private static BigDecimal flatRate = new BigDecimal(String.valueOf(0.001));

	public CheckingAccount(int accountNumber) {
		super(Account.CHECKING, accountNumber);
	}

	/*
	 * Implements Account.getCurrentInterestRate() for checking accounts (as a
	 * flat rate of 0.1% per annum)
	 */
	public BigDecimal getInterestRateForBalance() {
		return flatRate;
	}

	public BigDecimal interestEarned() {
		return balance.multiply(flatRate);
	}
}
