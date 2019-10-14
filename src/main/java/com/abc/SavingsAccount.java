package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {

	public SavingsAccount(int accountNumber) {
		super(accountNumber);

		setAccountName("Savings Account");
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
		// Add negative amount to transaction list to indicate a withdrawal
		getTransactions().add(new Transaction(amount.negate()));

		return true;
	}

	@Override
	public void dailyTasks() {
		compoundSplitInterest();
	}

	private void compoundSplitInterest() {
		// Base rate amount is $1000
		BigDecimal baseAmount = new BigDecimal(1000);

		if (getBalance().compareTo(baseAmount) > 0) {
			// Calculate the amount to be compounded at 2%
			BigDecimal remainingAmount = getBalance().subtract(baseAmount);

			setBalance(getBalance().add(compoundInterest(baseAmount)));

			// Increase interest rate to 2%
			setInterestRate(2);
			setBalance(getBalance().add(compoundInterest(remainingAmount)));

			// Reset interest rate to 1%
			setInterestRate(1);
		} else {
			// If there is less than $1000, then just calculate the interest on the total
			// balance
			compoundInterest(getBalance());
		}
	}

}
