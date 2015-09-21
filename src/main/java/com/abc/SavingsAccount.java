package com.abc;

import java.math.BigDecimal;

public class SavingsAccount extends Account {

	public static final int SAVINGS = 1;
	
	public SavingsAccount(int accountType) {
		super(accountType);
	}

	public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        if (amount.compareTo(new BigDecimal(1000)) < 1) // $1000 or less
        	return amount.multiply(new BigDecimal(0.001));
        else { 
        	//0.1% for first 1000 then 0.2%
        	amount = amount.subtract(new BigDecimal(1000));
        	amount = amount.multiply(new BigDecimal(0.002));
        	amount = amount.add(BigDecimal.ONE);
        	return amount;
        }
    }

}
