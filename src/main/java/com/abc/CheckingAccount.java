package com.abc;

/**
 * A Checking Account.
 */
public class CheckingAccount extends Account
{
	public CheckingAccount(String accountNumber) {
		super(accountNumber);
	}

	/**
	 * {@inheritDoc}
	 */
    public double interestEarned() {
        double amount = sumTransactions();
        double interestRate = 0.001d;
		double dailyInterestRate = calcualteDailyInerestRate(interestRate);
		return amount * dailyInterestRate; 
    }

	/**
	 * {@inheritDoc}
	 */
    @Override
    public String getAccountName() {
    	return "Checking Account";
    }
}
