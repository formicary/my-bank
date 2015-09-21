package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
	
	public static final int CHECKING = 0;
	
	public CheckingAccount(int accountType) {
        //this.accountType = accountType;
		super(accountType);
	}

	@Override
    public BigDecimal interestEarned() {
        BigDecimal amount = sumTransactions();
        return amount.multiply(new BigDecimal(0.001));
    }
    
}
