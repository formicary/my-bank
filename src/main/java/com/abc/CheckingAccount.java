package com.abc;

public class CheckingAccount extends Account {

	private static final double ANNUAL_INTEREST = 0.001;

	public CheckingAccount() {
		super(Account.CHECKING, false);
	}

	public CheckingAccount(boolean debuggingEnabled) {
		super(Account.CHECKING, debuggingEnabled);
	}

	public void applyInterest() {
		double amount = sumTransactions();
		int days = daysSinceLastTransaction();
		if (amount > 0 && days > 0) {
			double yearLength = DateProvider.getInstance().getYearLength();
			
			double dailyInterestRate = ANNUAL_INTEREST / yearLength;
			double dailyInterestRateRaised = Math.pow((1 + dailyInterestRate), days);
			double interestEarned = (amount * (dailyInterestRateRaised)) - amount;

			interest(Math.floor(interestEarned * 100) / 100);
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
