package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
	
	public CheckingAccount() {
		
		accountType = "Checking Account";
	}
	
	@Override
    public BigDecimal interestEarned() {
    	
		synchronized (this) {
			
			BigDecimal rate = new BigDecimal(ONE_TENTH_PERCENT);
			//total += balance * rate;
			BigDecimal total = balance.multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP);
			
    		return total;
    	}
    }

	@Override
	public BigDecimal compoundDailyInterest() {
		
		synchronized (this) {
			
			BigDecimal rate = new BigDecimal(ONE_TENTH_PERCENT / YEAR);
			//balance += balance * rate;
			balance = balance.add(balance.multiply(rate))
					.setScale(2, BigDecimal.ROUND_HALF_UP);
			
			//System.out.println("balance: "+balance);
			return balance;
		}
	}

}
