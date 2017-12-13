package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.banking.config.ApplicationConfig;

public final class CheckingAccount extends AbstractAccount {

	public final BigDecimal FLAT_INTEREST_RATE_PER_ANNUM = new BigDecimal("0.01");
	
	protected CheckingAccount() {
		super();
	}
		
	public String getAccountTypeName() {
		return "Checking Account";
	}

	@Override
	public BigDecimal getDailyInterest(LocalDate day) {
		
		BigDecimal balance = getBalanceAt(day);

        return balance
        			.multiply(FLAT_INTEREST_RATE_PER_ANNUM)
        			.divide(new BigDecimal(day.lengthOfYear()), 
        					ApplicationConfig.monetaryDecimalPlacesAllowed, 
        					ApplicationConfig.monetaryRoundingMode);
	}
}
