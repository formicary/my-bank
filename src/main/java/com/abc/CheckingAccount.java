package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {

	public CheckingAccount(int accountNumber) {
		super(accountNumber);

		setAccountName("Checking Account");
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

		return true;
	}

	@Override
	public void dailyTasks() {
		setBalance(getBalance().add(compoundInterest(getBalance())));
	}

}
