package com.abc;

import java.math.BigDecimal;

public class MaxiSavingsAccount extends Account {
	
	//public static final String accountType = "Maxi Savings Account";
	
	public MaxiSavingsAccount() {
		
		accountType = "Maxi Savings Account";
	}

	@Override
	public BigDecimal interestEarned() {	
    	
		synchronized (this) {

			BigDecimal rate;
			
			if (getWithdrawalGap() <= WITHDRAWALS_DAY_GAP)			
				rate = new BigDecimal(ONE_TENTH_PERCENT);
	        else 
	        	rate = new BigDecimal(FIVE_PERCENT);
	        	
			//total = balance * rate;
			BigDecimal total = balance.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
			return total;
		}  
	}

	@Override
	public BigDecimal compoundDailyInterest() {

		synchronized (this) {
			
			//System.out.println("balance before: "+balance);
			BigDecimal rate;
			
			if (getWithdrawalGap() <= WITHDRAWALS_DAY_GAP)			
				rate = new BigDecimal(ONE_TENTH_PERCENT / YEAR);
            else
            	rate = new BigDecimal(FIVE_PERCENT / YEAR);
		
			//balance += balance * rate;
			/*
			BigDecimal test = balance.multiply(rate)
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			System.out.println("test to add: "+test);
			*/
			
			balance = balance.add(balance.multiply(rate))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			//System.out.println("balance after: "+balance);
			return balance;
		}
		
	}
}
