package com.abc;

import java.time.LocalDate;

public class Maxisavings extends Account {
	
	public static final int MAXI_SAVINGS = 2;

	public Maxisavings() {
		super(MAXI_SAVINGS);
	}
	
	@Override
	//How interest is calculated for MAXI_SAVINGS accounts.
	public double interestEarned() {
		double amount = sumTransactions();
		if (!getTransactions().isEmpty()) {
			if (checkWithdrawal()) {
				return amount * 0.001;
   			}         		
		} 
		return amount * 0.05;
	}
	
	//Method to check if there has been a withdrawal within the past 10 days.
	private boolean checkWithdrawal() {
    	DateProvider dp = DateProvider.getInstance();
    	LocalDate current = dp.getDateNoTime();
    	LocalDate past = dp.minusTen(current);
    	
    	boolean extremes = getTransactions().stream()
				 					   		.filter(t -> dp.toLocalDate(t.getTime()).isEqual(past) || dp.toLocalDate(t.getTime()).isEqual(current))
				 					   		.anyMatch(t -> t.getAmount() < 0);

    	boolean inBetween = getTransactions().stream()
				   							 .filter(t -> dp.toLocalDate(t.getTime()).isAfter(past) && dp.toLocalDate(t.getTime()).isBefore(current))
				   							 .anyMatch(t -> t.getAmount() < 0);		
		
    	if (!extremes && !inBetween) {
			return false;
		}		
    	return true;
    }
	
}
