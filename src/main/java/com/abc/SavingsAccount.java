package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {
	
	//public static final String accountType = "Savings Account";

	public SavingsAccount() {
		
		accountType = "Savings Account";
	}
	
	@Override
    public synchronized BigDecimal interestEarned() {
		
		synchronized (this) {
			
			BigDecimal lowerRate = new BigDecimal(ONE_TENTH_PERCENT);
			BigDecimal higherRate = new BigDecimal(TWO_TENTH_PERCENT);
			BigDecimal total = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			//if balance lower that limit
			if (balance.compareTo(LOWER_LIMIT) <= 0)
				//total = balance * lowerRate;
				total = balance.multiply(lowerRate);		        
		    else
		    	//total = LOWER_LIMIT * lowerRate + (balance - LOWER_LIMIT) * higherRate
		    	total = lowerRate.multiply(LOWER_LIMIT)
		    				.add(higherRate.multiply(balance.subtract(LOWER_LIMIT)))
		    				.setScale(2, BigDecimal.ROUND_HALF_UP);
		        		
			return total;
		}
	}

	@Override
	public BigDecimal compoundDailyInterest() {

		synchronized (this) {
			
			BigDecimal lowerRate = new BigDecimal(ONE_TENTH_PERCENT / YEAR);
			BigDecimal higherRate = new BigDecimal(TWO_TENTH_PERCENT / YEAR);
			
			//if balance lower that limit
			if (balance.compareTo(LOWER_LIMIT) <= 0)	
				//balance += balance * lowerRate
				balance = balance.add(balance.multiply(lowerRate))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
		    else	    	
		    	//balance += LOWER_LIMIT * lowerRate + (balance - LOWER_LIMIT) * higherRate
		    	balance = balance.add(lowerRate.multiply(LOWER_LIMIT))
		    			.add(higherRate.multiply(balance.subtract(LOWER_LIMIT)))
		    			.setScale(2, BigDecimal.ROUND_HALF_UP);
		    	
			//System.out.println("Savings balance: "+balance);
			return balance;
		}		
	}
}
