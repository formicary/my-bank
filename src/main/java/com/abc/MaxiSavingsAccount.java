package com.abc;

import java.util.Date;

public class MaxiSavingsAccount extends Account {

	private static final AccountType TYPE = AccountType.MAXI_SAVINGS;
	private static final double INTEREST_RATE = 0.05 / DAYS;
	private static final double WITHDRAWN_INTEREST_RATE = 0.001 / DAYS;
	private Date lastWithdrawal = null;
	
	public void withdraw(double amount) {
		super.withdraw(amount);
		lastWithdrawal = DateProvider.getInstance().now();
	}
	
	public double interestEarned() {
		double balance = getBalance();
		double interestRate = withdrawnInTenLastDays() ? WITHDRAWN_INTEREST_RATE : INTEREST_RATE;
		double interestEarned = balance * interestRate;
		increaseTotalInterestEarned(interestEarned);
		return interestEarned;
	}
	
	private boolean withdrawnInTenLastDays() {
		Date currentDate = DateProvider.getInstance().now();
		if(lastWithdrawal != null) {
			// Gets the time difference in milliseconds
			long difference = currentDate.getTime() - lastWithdrawal.getTime();
			// Convert the difference into days
			int days = (int) difference / (1000 * 60 * 60 * 24);
			return days > 10 ? false : true;
		}
		return false;
	}

	public AccountType getType() {
		return TYPE;
	}

}
