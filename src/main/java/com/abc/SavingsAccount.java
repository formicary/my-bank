package com.abc;

import java.util.Calendar;

public final class SavingsAccount extends Account{
	private final double interestrate1 = 0.001;
	private final double interestrate2 = 0.002;
	public SavingsAccount(){
		super(accountTypes.Savings);
	}
	
	/* Calculate interest earned for each transaction. When the total amount in account is less than 1000
	 * we only need to perform one calculation for interest. If it is greater than 1000, we need to
	 * perform two calculation, one for the first 1000 with interest rate of 0.001 and the rest of the amount
	 * with interest rate of 0.002 and add the total interest from both calculation together.
	 */
	@Override
	public double interestEarned() {
		double totalinterest = 0.0;
		double totalamount = 0.0;

		 for (int i = 0; i < transactions.size(); i++){
			 ITransaction t = transactions.get(i);
			 Calendar Date2 = getCompoundperiod(i,transactions.size());
			 totalamount += t.getTransactionAmount(); 
			 if (totalamount < 1000){
				 totalinterest += calcinterest(totalamount,t.getTransactionDate(),Date2,interestrate1,0.0);
			 }else{
				 double temp = calcinterest(1000,t.getTransactionDate(),Date2,interestrate1,0.0);
				 totalinterest += calcinterest(totalamount-1000,t.getTransactionDate(),Date2,interestrate2,totalinterest);
				 totalinterest += temp;
			}
		}
		return totalinterest;
    }
}
