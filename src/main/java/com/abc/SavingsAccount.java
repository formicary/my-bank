package com.abc;

/**
 * 
 * Savings account
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class SavingsAccount extends AbstractAccount{
    
	/**
     * Constructor 
     * @param customer The customer this account belongs to
     * @param balance The starting balance
     */
	public SavingsAccount(Customer customer, double balance) {
		super(customer, balance);
		setType(AccountEnum.SAVINGSACCOUNT);
	}

	/*
	 * (non-Javadoc)
	 * @see com.abc.Account#interestEarned()
	 */
	public double interestEarned() {
		double interest = 0;
        if (balance <= 1000) {
        	interest = balance * 0.001;
        	balance += interest;
            return interest;
        }
        	interest = 1 + (balance-1000) * 0.002;
        	balance += interest;
            return interest;	
	}

}
