package com.abc;

import java.math.BigDecimal;

public class MaxiSavingsAccount extends Account {

	public static final int MAXI_SAVINGS = 2;
	
	public MaxiSavingsAccount(int accountType) {
		super(accountType);
		
	}
	
	public BigDecimal interestEarned() {
		BigDecimal amount = sumTransactions();
		if(Transaction.sinceLast())
			return amount.multiply(new BigDecimal(0.001));
		
		return amount.multiply(new BigDecimal(0.05));
		}
	
	/*
	public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        BigDecimal total = BigDecimal.ZERO;
        if (amount.compareTo(new BigDecimal(1000)) == 1){
        	total = new BigDecimal(20);
        	amount = amount.subtract(new BigDecimal(1000));
        }
        else 
        	return amount.multiply(new BigDecimal(0.02));
        
        if (amount.compareTo(new BigDecimal(1000)) == 1){
        	total = total.add(new BigDecimal(50));
        	amount = amount.subtract(new BigDecimal(1000));
        }
        else {
        	amount = amount.multiply(new BigDecimal(0.05));
        	total = total.add(amount);
        	return total;
        }
        
        amount = amount.multiply(new BigDecimal(0.1));
        total = total.add(amount);
        return total;
    }
    */
}
