package com.abc;

public class CheckingAccount extends Account {

	public CheckingAccount(Customer customer, AccountType accountType) {
		super(customer, accountType);
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		return amount * 0.001;
	}

}
