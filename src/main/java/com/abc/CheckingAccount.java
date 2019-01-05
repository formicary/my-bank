package com.abc;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends Account {

	public CheckingAccount() {
		this.transactions = new ArrayList<Transaction>();
	}

	@Override
	public double interestEarned() {
		double amount = sumTransactions();
		return amount * 0.001;
	}
}
