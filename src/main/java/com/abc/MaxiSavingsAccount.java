package com.abc;

public class MaxiSavingsAccount extends Account {
	
	private final static String accountType = "MAXISAVINGS";

	public MaxiSavingsAccount() {
		super(accountType);
	}

	@Override
	public double interestEarned() {
		double amount = this.sumTransactions();
		int minDays = 10;
		for (Transaction transaction : transactions) {
			int pastDays = getPeriod(transaction.getTransactionLocalDate());
			if (pastDays < minDays && transaction.getAmount() < 0)
				return amount * 0.001;
		}
		return amount * 0.05;
	}

}
