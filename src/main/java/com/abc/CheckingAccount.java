package com.abc;

public class CheckingAccount extends Account {

	public CheckingAccount() {
		super(Account.CHECKING, false);
	}
	
	public CheckingAccount(boolean debuggingEnabled) {
		super(Account.CHECKING, debuggingEnabled);
	}

	public void applyInterest() {
		double amount = sumTransactions();
		if (amount > 0 && daysSinceLastTransaction() > 0) {
			interest(amount * 0.001);
		}
	}

	public double getBalance() {
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
