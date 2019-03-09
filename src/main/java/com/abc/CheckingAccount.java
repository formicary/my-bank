package com.abc;

import java.util.Calendar;

public final class CheckingAccount extends Account{
	private final double interestrate = 0.001;
	public CheckingAccount(){
		super(accountTypes.Checking);
	}
	
	/* Calculate all interest earned in the account by iterating through the transaction
	 * and using the formula (method) in account class.
	 */
	@Override
	public double interestEarned() {
		double interest = 0.0;
		double totalamount = 0.0;
		for (int i = 0; i < transactions.size(); i++){
			ITransaction t = transactions.get(i);
			totalamount += t.getTransactionAmount();
			
			//Get the number of days between one transaction to another
			Calendar Date2 = getCompoundperiod(i,transactions.size());
			
			//Calculate the interest using the formula
			interest += calcinterest(totalamount, t.getTransactionDate(),Date2,interestrate,interest);
		}
		return interest;
    }
	
	
	
}
