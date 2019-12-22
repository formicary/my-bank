package com.abc;

public class CheckingAccount extends Account {

	public CheckingAccount() {
		super(Account.CHECKING);
	}

	public double interestEarned() {
		double amount = sumTransactions();
		return amount * 0.001;
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

}
