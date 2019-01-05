package com.abc;

import java.util.ArrayList;
import java.util.List;

public class SavingsAccount extends Account {

	public SavingsAccount() {
		this.transactions = new ArrayList<Transaction>();
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		if (amount <= 1000)
			return amount * 0.001;
		else
			return 1 + (amount - 1000) * 0.002;
	}
}
