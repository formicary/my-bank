package com.abc;

/**
 * A Checking Account.
 */
public class SavingsAccount extends Account
{
	public SavingsAccount(String accountNumber) {
		super(accountNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		if (amount <= 1000) {
			return amount * 0.001;
		} else {
			return 1 + (amount - 1000) * 0.002;
		}
	}
    
	/**
	 * {@inheritDoc}
	 */
    @Override
    public String getAccountName() {
    	return "Savings Account";
    }
}
