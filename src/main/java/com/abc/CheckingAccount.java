package com.abc;

/**
 * A Checking Account.
 */
public class CheckingAccount extends Account
{
	/**
	 * {@inheritDoc}
	 */
    public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }
    
	/**
	 * {@inheritDoc}
	 */
    @Override
    public String getAccountName() {
    	return "Checking Account";
    }
}
