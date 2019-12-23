package com.abc;

public class MaxiSavingsAccount extends Account {
	
//	Day threshold for interest rates changes.
	private static final int DAYS_THRESHOLD = 10;
	
	private static final double PRE_THRESHOLD_ANNUAL_INTEREST = 0.001;
	private static final double POST_THRESHOLD_ANNUAL_INTEREST = 0.05;

	public MaxiSavingsAccount() {
		super(Account.MAXI_SAVINGS, false);
	}
	
	public MaxiSavingsAccount(boolean debuggingEnabled) {
		super(Account.MAXI_SAVINGS, debuggingEnabled);
	}

	public void applyInterest() {
		double amount = sumTransactions();
		int days = daysSinceLastTransaction();
//		Need to have money in the account and have not had interest
//		applied in the last day for interest to be applied again.
		if (amount > 0 && days > 0) {
			int yearLength = DateProvider.getInstance().getYearLength();
			if (daysSinceLastWithdrawal() <= DAYS_THRESHOLD) {
				
				double dailyInterestRate = PRE_THRESHOLD_ANNUAL_INTEREST / yearLength;
				double dailyInterestRateRaised = Math.pow((1 + dailyInterestRate), days);
				double interestEarned = (amount * (dailyInterestRateRaised)) - amount;

				interest(Math.floor(interestEarned * 100) / 100);
				
			} else {
				
				double dailyInterestRate = POST_THRESHOLD_ANNUAL_INTEREST / yearLength;
				double dailyInterestRateRaised = Math.pow((1 + dailyInterestRate), days);
				double interestEarned = (amount * (dailyInterestRateRaised)) - amount;

				interest(Math.floor(interestEarned * 100) / 100);
				
			}	
		
		}
	}
	
    private int daysSinceLastWithdrawal() {
    	if (lastWithdrawal != null) {
    	long currentTime = DateProvider.getInstance().now().getTime();
    	long lastWithdrawalTime = lastWithdrawal.getTime();
    	long difference = currentTime - lastWithdrawalTime;
    	long differenceDays = (difference / (1000 * 60 * 60 * 24));
    	
    	return (int) differenceDays;
    	} else {
//    		Return an amount greater than the threshold since a withdrawal
//    		hasn't been made.
    		return DAYS_THRESHOLD+1;
    	}
    }
	
}
