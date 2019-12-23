package com.abc;

public class SavingsAccount extends Account {

	private static final double THRESHOLD_AMOUNT = 1000;
	private static final double PRE_THRESHOLD_ANNUAL_INTEREST = 0.001;
	private static final double POST_THRESOLD_ANNUAL_INTEREST = 0.002;

	public SavingsAccount() {
		super(Account.SAVINGS, false);
	}

	public SavingsAccount(boolean debuggingEnabled) {
		super(Account.SAVINGS, debuggingEnabled);
	}

	public void applyInterest() {
		double amount = sumTransactions();
		int days = daysSinceLastTransaction();

//		Need to have money in the account and have not had interest
//		applied in the last day for interest to be applied again.
		if (amount > 0 && days > 0) {

			int yearLength = DateProvider.getInstance().getYearLength();
			double preThresholdDailyInterestRate = PRE_THRESHOLD_ANNUAL_INTEREST / yearLength;

			if (amount <= THRESHOLD_AMOUNT) {

				double dailyInterestRateRaised = Math.pow((1 + preThresholdDailyInterestRate), days);
				double interestEarned = (amount * (dailyInterestRateRaised)) - amount;
				interest(Math.floor(interestEarned * 100) / 100);

			} else {

				double postThresholdDailyInterestRate = POST_THRESOLD_ANNUAL_INTEREST / yearLength;

				double preThresholdDailyInterestRateRaised = Math.pow((1 + preThresholdDailyInterestRate), days);
				double preThresholdInterestEarned = (THRESHOLD_AMOUNT * (preThresholdDailyInterestRateRaised))
						- THRESHOLD_AMOUNT;

				double postThresholdDailyInterestRateRaised = Math.pow((1 + postThresholdDailyInterestRate), days);
				double postThresoldInterestEarned = ((amount - THRESHOLD_AMOUNT)
						* (postThresholdDailyInterestRateRaised)) - (amount - THRESHOLD_AMOUNT);

				interest(Math.floor((preThresholdInterestEarned + postThresoldInterestEarned) * 100) / 100);
			}
		}
	}

}
