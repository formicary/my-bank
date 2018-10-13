package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class CheckingAccount extends AbstractAccount {

	/**
     * Constructor 
     * @param customer The customer this account belongs to
     * @param balance The starting balance
     */
	public CheckingAccount(Customer customer, double balance) {
		super(customer, balance);
		setType(AccountEnum.CHECKINGACCOUNT);
	}  
	
	/*
	 * (non-Javadoc)
	 * @see com.abc.Account#interestEarned()
	 */
	public double interestEarned() {
		 double interest = balance * 0.001;
		 balance += interest;
	     return interest;
	}

	
}
